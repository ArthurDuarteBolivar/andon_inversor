package com.api.nodemcu.controllers.inversor;

import com.api.nodemcu.Services.TimerService;
import com.api.nodemcu.model.NodemcuModel;
import com.api.nodemcu.model.OperationModel;
import com.api.nodemcu.model.RealizadoHorariaModel;
import com.api.nodemcu.repository.MainRepository;
import com.api.nodemcu.repository.NodemcuRepository;
import com.api.nodemcu.repository.OperationRepository;
import com.api.nodemcu.repository.RealizadoHorariaRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/nodemcu")
public class NodemcuControllerInversor {


    @Autowired
    private NodemcuRepository repository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private MainRepository mainRepostory;

    @Autowired
    private RealizadoHorariaRepository realizadoHorariaRepository;

    @Autowired
    private TimerService timerService;

    boolean state = false;
    Integer anterior = 0;

    @GetMapping()
    public List<NodemcuModel> list() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            zerarDados(); // Chame a função desejada aqui
        };

        // Agende a tarefa para ser executada a cada hora
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public NodemcuModel findByName(@PathVariable String name) {
        OperationModel operation = operationRepository.findByName(name);
        NodemcuModel nodemcu = repository.findByNameId(operation);
        return nodemcu;
    }


    @PostMapping()
    public NodemcuModel post(@RequestBody NodemcuModel device) {
        repository.save(device);
        return device;
    }



    @Transactional
    @PatchMapping("/{name}")
    public NodemcuModel patch(@PathVariable String name, @RequestBody NodemcuModel nodemcuUpdates) throws IOException, InterruptedException {
        OperationModel operation = operationRepository.findByName(name);
        NodemcuModel device = repository.findByNameId(operation);


        if (device == null) {
            repository.save(nodemcuUpdates);
            return nodemcuUpdates;
        }

        device.setThirdlastTC(device.getSecondtlastTC());
        device.setSecondtlastTC(device.getFirtlastTC());
        device.setFirtlastTC(device.getCurrentTC());
        if (device.getShortestTC() > nodemcuUpdates.getShortestTC() && nodemcuUpdates.getShortestTC() > 10) {
            device.setShortestTC(nodemcuUpdates.getShortestTC());
        } else if (mainRepostory.findById(1).get().getTCimposto() < nodemcuUpdates.getCurrentTC()) {
            Integer excedido = device.getQtdeTCexcedido();
            excedido++;
            device.setQtdeTCexcedido(excedido);
        }

        Integer media = (device.getTCmedio() + nodemcuUpdates.getCurrentTC()) / 2;

        device.setTCmedio(media);
        device.setCount(nodemcuUpdates.getCount());
        device.setState(nodemcuUpdates.getState());
        device.setCurrentTC(nodemcuUpdates.getCurrentTC());
        device.setMaintenance(nodemcuUpdates.getMaintenance());

        try {
            NodemcuModel savedDevice = repository.save(device);
            if (savedDevice != null) {
                if (nodemcuUpdates.getNameId().getName().equals("I60")) {
                    RealizadoHoraria();
                }
            } else {
                NodemcuModel newSavedDevice = repository.save(device);
                if(newSavedDevice != null){
                    if (nodemcuUpdates.getNameId().getName().equals("I60")) {
                        RealizadoHoraria();
                    }
                }else {
                    System.out.println("Erro");
                    NodemcuModel otherNewSavedDevice = repository.save(device);
                    if(otherNewSavedDevice != null){
                        if (nodemcuUpdates.getNameId().getName().equals("I60")) {
                            RealizadoHoraria();
                        }
                    }
                }
            }
            return device;
        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar o dispositivo no banco de dados", e);
        }
    }

    @GetMapping("/atualizarState/{name}/{state}")
    public void atualizarCor(@PathVariable("name") String name, @PathVariable("state") String state) {

        OperationModel operation = operationRepository.findByName(name);
        NodemcuModel device = repository.findByNameId(operation);
        device.setState(state);
        repository.save(device);
    }


    public void RealizadoHoraria() {
        Date agora = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("HH");
        Integer horaFormatada = Integer.parseInt(formatador.format(agora));
        Optional<RealizadoHorariaModel> realizado = Optional.of(new RealizadoHorariaModel());
        Integer hour = 0;
        realizado = realizadoHorariaRepository.findById(1);
        OperationModel operation = operationRepository.findByName("160");
        NodemcuModel device = repository.findByNameId(operation);
        switch (horaFormatada) {
            case 7:
                hour = realizado.get().getHoras7();
                hour++;
                realizado.get().setHoras7(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 8:
                hour = realizado.get().getHoras8();
                hour++;
                realizado.get().setHoras8(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 9:
                hour = realizado.get().getHoras9();
                hour++;
                realizado.get().setHoras9(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 10:
                hour = realizado.get().getHoras10();
                hour++;
                realizado.get().setHoras10(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 11:
                hour = realizado.get().getHoras11();
                hour++;
                realizado.get().setHoras11(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 12:
                hour = realizado.get().getHoras12();
                hour++;
                realizado.get().setHoras12(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 13:
                hour = realizado.get().getHoras13();
                hour++;
                realizado.get().setHoras13(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 14:
                hour = realizado.get().getHoras14();
                hour++;
                realizado.get().setHoras14(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 15:
                hour = realizado.get().getHoras15();
                hour++;
                realizado.get().setHoras15(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 16:
                hour = realizado.get().getHoras16();
                hour++;
                realizado.get().setHoras16(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;

            case 17:
                hour = realizado.get().getHoras17();
                hour++;
                realizado.get().setHoras17(hour);
                realizadoHorariaRepository.save(realizado.get());
                break;
        }
        device.setCount(realizadoHorariaRepository.somarTudo());
        repository.save(device);
    }
    @Transactional
    @GetMapping("/atualizarTempo/{name}/{tempo}")
    public void iniciarTempo(@PathVariable("name") String name, @PathVariable("tempo") Integer tempo) {
        OperationModel operation = operationRepository.findByName(name);
        System.out.println(operation);
        repository.updateLocalTCByNameId(tempo ,operation.getId());
        System.out.println(tempo);
        System.out.println(operation.getId());
    }



    public void zerarDados() {
        Date date = new Date();
        if (date.getHours() >= 20 && date.getMinutes() >= 50 && date.getHours() <= 21) {
            Optional<RealizadoHorariaModel> realizadoReset = realizadoHorariaRepository.findById(1);
            realizadoReset.get().setHoras12(0);
            realizadoReset.get().setHoras11(0);
            realizadoReset.get().setHoras10(0);
            realizadoReset.get().setHoras9(0);
            realizadoReset.get().setHoras8(0);
            realizadoReset.get().setHoras7(0);
            realizadoReset.get().setHoras13(0);
            realizadoReset.get().setHoras14(0);
            realizadoReset.get().setHoras15(0);
            realizadoReset.get().setHoras17(0);
            realizadoReset.get().setHoras16(0);
            realizadoReset.get().setHoras12(0);
            realizadoHorariaRepository.save(realizadoReset.get());
            List<NodemcuModel> nodemcu = repository.findAll();
            for (int i = 0; i < nodemcu.size(); i++) {
                nodemcu.get(i).setCurrentTC(0);
                nodemcu.get(i).setCount(0);
                nodemcu.get(i).setFirtlastTC(0);
                nodemcu.get(i).setSecondtlastTC(0);
                nodemcu.get(i).setThirdlastTC(0);
                nodemcu.get(i).setState("verde");
                nodemcu.get(i).setMaintenance(0);
                nodemcu.get(i).setQtdeTCexcedido(0);
                nodemcu.get(i).setTCmedio(0);
                nodemcu.get(i).setShortestTC(9999);
                nodemcu.get(i).setLocalTC(0);
                nodemcu.get(i).setCount(0);
                repository.save(nodemcu.get(i));
            }
            for (int i = 0; i < nodemcu.size(); i++) {
                nodemcu.get(i).setCurrentTC(0);
                nodemcu.get(i).setCount(0);
                nodemcu.get(i).setFirtlastTC(0);
                nodemcu.get(i).setSecondtlastTC(0);
                nodemcu.get(i).setThirdlastTC(0);
                nodemcu.get(i).setState("verde");
                nodemcu.get(i).setMaintenance(0);
                nodemcu.get(i).setQtdeTCexcedido(0);
                nodemcu.get(i).setTCmedio(0);
                nodemcu.get(i).setShortestTC(9999);
                nodemcu.get(i).setLocalTC(0);
                nodemcu.get(i).setCount(0);
                repository.save(nodemcu.get(i));
            }
        }
    }


}
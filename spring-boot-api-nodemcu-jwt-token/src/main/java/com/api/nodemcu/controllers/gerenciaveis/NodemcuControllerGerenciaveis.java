package com.api.nodemcu.controllers.gerenciaveis;

import com.api.nodemcu.Services.TimerService;
import com.api.nodemcu.model.*;
import com.api.nodemcu.repository.*;

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
@RequestMapping("/api/v1/gerenciaveis")
public class NodemcuControllerGerenciaveis {


    @Autowired
    private NodemcuRepositoryGerenciaveis repository;

    @Autowired
    private OperationRepositoryGerenciaveis operationRepository;

    @Autowired
    private MainRepositoryGerenciaveis mainRepostory;

    @Autowired
    private RealizadoHorariaRepositoryGerenciaveis realizadoHorariaRepository;

    @Autowired
    private TimerService timerService;

    boolean state = false;
    Integer anterior = 0;

    @GetMapping()
    public List<NodemcuModelGerenciaveis> list() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            zerarDados(); // Chame a função desejada aqui
        };

        // Agende a tarefa para ser executada a cada hora
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public NodemcuModelGerenciaveis findByName(@PathVariable String name) {
        OperationModelGerenciaveis operation = operationRepository.findByName(name);
        NodemcuModelGerenciaveis nodemcu = repository.findByNameId(operation);
        return nodemcu;
    }


    @PostMapping()
    public NodemcuModelGerenciaveis post(@RequestBody NodemcuModelGerenciaveis device) {
        repository.save(device);
        return device;
    }



    @Transactional
    @PatchMapping("/{name}")
    public NodemcuModelGerenciaveis patch(@PathVariable String name, @RequestBody NodemcuModelGerenciaveis nodemcuUpdates) throws IOException, InterruptedException {
        OperationModelGerenciaveis operation = operationRepository.findByName(name);
        NodemcuModelGerenciaveis device = repository.findByNameId(operation);


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
            NodemcuModelGerenciaveis savedDevice = repository.save(device);
            if (savedDevice != null) {
                if (nodemcuUpdates.getNameId().getName().equals("G40")) {
                    RealizadoHoraria();
                }
            } else {
                NodemcuModelGerenciaveis newSavedDevice = repository.save(device);
                if(newSavedDevice != null){
                    if (nodemcuUpdates.getNameId().getName().equals("G40")) {
                        RealizadoHoraria();
                    }
                }else {
                    System.out.println("Erro");
                    NodemcuModelGerenciaveis otherNewSavedDevice = repository.save(device);
                    if(otherNewSavedDevice != null){
                        if (nodemcuUpdates.getNameId().getName().equals("G40")) {
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

        OperationModelGerenciaveis operation = operationRepository.findByName(name);
        NodemcuModelGerenciaveis device = repository.findByNameId(operation);
        device.setState(state);
        repository.save(device);
    }


    public void RealizadoHoraria() {
        Date agora = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("HH");
        Integer horaFormatada = Integer.parseInt(formatador.format(agora));
        Optional<RealizadoHorariaModelGerenciaveis> realizado = Optional.of(new RealizadoHorariaModelGerenciaveis());
        Integer hour = 0;
        realizado = realizadoHorariaRepository.findById(1);
        OperationModelGerenciaveis operation = operationRepository.findByName("G40");
        NodemcuModelGerenciaveis device = repository.findByNameId(operation);
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
        OperationModelGerenciaveis operation = operationRepository.findByName(name);
        repository.updateLocalTCByNameId(tempo ,operation.getId());
    }

//    @GetMapping("/atualizarTempo/{name}/{tempo}")
//    public void iniciarTempo(@PathVariable("name") String name, @PathVariable("tempo") Integer tempo) {
//        OperationModelGerenciaveis operation = operationRepository.findByName(name);
//        NodemcuModelGerenciaveis device = repository.findByNameId(operation);
//        if(tempo == 0){
//            device.setLocalTC(0);
//            repository.save(device);
//            return;
//        }
//        device.setLocalTC(tempo);
//        repository.save(device);
//    }


    public void zerarDados() {
        Date date = new Date();
        if (date.getHours() >= 20 && date.getMinutes() >= 50 && date.getHours() <= 21) {
            Optional<RealizadoHorariaModelGerenciaveis> realizadoReset = realizadoHorariaRepository.findById(1);
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
            List<NodemcuModelGerenciaveis> nodemcu = repository.findAll();
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
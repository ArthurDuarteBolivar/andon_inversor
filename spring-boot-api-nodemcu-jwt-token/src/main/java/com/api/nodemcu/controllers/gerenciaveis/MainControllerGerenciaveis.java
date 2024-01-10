package com.api.nodemcu.controllers.gerenciaveis;

import com.api.nodemcu.model.ImpostoDTO;
import com.api.nodemcu.model.MainModel;
import com.api.nodemcu.model.MainModelGerenciaveis;
import com.api.nodemcu.repository.MainRepository;
import com.api.nodemcu.repository.MainRepositoryGerenciaveis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main_gerenciaveis")
public class MainControllerGerenciaveis {

    @Autowired
    MainRepositoryGerenciaveis repostory;

    @GetMapping()
    public List<MainModelGerenciaveis> listAll(){
        return repostory.findAll();
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Integer id, @RequestBody ImpostoDTO dto) {
        MainModelGerenciaveis main = repostory.findById(id).get();
        main.setImposto(dto.getImposto());
        main.setTCimposto(dto.getTCimposto());
        main.setShiftTime(dto.getShiftTime());
        repostory.save(main);
    }




}

package com.api.nodemcu.controllers.gerenciaveis;

import com.api.nodemcu.model.RealizadoHorariaModelGerenciaveis;
import com.api.nodemcu.repository.RealizadoHorariaRepositoryGerenciaveis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/realizadoHoraria_gerenciaveis")
public class RealizadoHorariaControllerGerenciaveis {

    @Autowired
    private RealizadoHorariaRepositoryGerenciaveis repository;

    @GetMapping()
    List<RealizadoHorariaModelGerenciaveis> findAll(){
        return  repository.findAll();
    }
}

package com.api.nodemcu.controllers.provedor;

import com.api.nodemcu.model.RealizadoHorariaModelGerenciaveis;
import com.api.nodemcu.model.RealizadoHorariaModelProvedor;
import com.api.nodemcu.repository.RealizadoHorariaRepositoryGerenciaveis;
import com.api.nodemcu.repository.RealizadoHorariaRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/realizadoHoraria_provedor")
public class RealizadoHorariaControllerProvedor {

    @Autowired
    private RealizadoHorariaRepositoryProvedor repository;

    @GetMapping()
    List<RealizadoHorariaModelProvedor> findAll(){
        return  repository.findAll();
    }
}

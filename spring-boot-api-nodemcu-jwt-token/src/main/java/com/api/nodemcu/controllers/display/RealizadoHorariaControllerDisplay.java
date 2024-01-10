package com.api.nodemcu.controllers.display;

import com.api.nodemcu.model.RealizadoHorariaModelDisplay;
import com.api.nodemcu.model.RealizadoHorariaModelProvedor;
import com.api.nodemcu.repository.RealizadoHorariaRepositoryDisplay;
import com.api.nodemcu.repository.RealizadoHorariaRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/realizadoHoraria_display")
public class RealizadoHorariaControllerDisplay {

    @Autowired
    private RealizadoHorariaRepositoryDisplay repository;

    @GetMapping()
    List<RealizadoHorariaModelDisplay> findAll(){
        return  repository.findAll();
    }
}

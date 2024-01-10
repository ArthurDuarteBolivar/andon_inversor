package com.api.nodemcu.controllers.inversor;

import com.api.nodemcu.model.RealizadoHorariaModel;
import com.api.nodemcu.repository.RealizadoHorariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/realizadoHoraria")
public class RealizadoHorariaControllerInversor {

    @Autowired
    private RealizadoHorariaRepository repository;

    @GetMapping()
    List<RealizadoHorariaModel> findAll(){
        return  repository.findAll();
    }
}

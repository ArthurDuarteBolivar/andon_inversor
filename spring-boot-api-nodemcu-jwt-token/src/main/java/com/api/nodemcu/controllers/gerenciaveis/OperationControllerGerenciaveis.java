package com.api.nodemcu.controllers.gerenciaveis;

import com.api.nodemcu.model.NodemcuModel;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.repository.OperationRepository;
import com.api.nodemcu.repository.OperationRepositoryGerenciaveis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operation_gerenciaveis")
public class OperationControllerGerenciaveis {


    @Autowired
    OperationRepositoryGerenciaveis repository;

    @PostMapping()
    public OperationModelGerenciaveis post(@RequestBody OperationModelGerenciaveis operation) {
        repository.save(operation);
        return operation;
    }

    @GetMapping("/{name}")
    public OperationModelGerenciaveis getByName(@PathVariable String name){
        return repository.findByName(name);
    }
}

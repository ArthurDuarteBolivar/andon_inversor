package com.api.nodemcu.controllers.provedor;

import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.model.OperationModelProvedor;
import com.api.nodemcu.repository.OperationRepositoryGerenciaveis;
import com.api.nodemcu.repository.OperationRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operation_provedor")
public class OperationControllerProvedor {


    @Autowired
    OperationRepositoryProvedor repository;

    @PostMapping()
    public OperationModelProvedor post(@RequestBody OperationModelProvedor operation) {
        repository.save(operation);
        return operation;
    }

    @GetMapping("/{name}")
    public OperationModelProvedor getByName(@PathVariable String name){
        return repository.findByName(name);
    }
}

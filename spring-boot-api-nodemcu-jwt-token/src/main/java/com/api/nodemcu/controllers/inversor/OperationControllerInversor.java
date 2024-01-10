package com.api.nodemcu.controllers.inversor;

import com.api.nodemcu.model.OperationModel;
import com.api.nodemcu.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operation")
public class OperationControllerInversor {


    @Autowired
    OperationRepository repository;

    @PostMapping()
    public OperationModel post(@RequestBody OperationModel operation) {
        repository.save(operation);
        return operation;
    }

    @GetMapping("/{name}")
    public OperationModel getByName(@PathVariable String name){
        return repository.findByName(name);
    }
}

package com.api.nodemcu.controllers.display;

import com.api.nodemcu.model.OperationModelDisplay;
import com.api.nodemcu.model.OperationModelProvedor;
import com.api.nodemcu.repository.OperationRepositoryDisplay;
import com.api.nodemcu.repository.OperationRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operation_display")
public class OperationControllerDisplay {


    @Autowired
    OperationRepositoryDisplay repository;

    @PostMapping()
    public OperationModelDisplay post(@RequestBody OperationModelDisplay operation) {
        repository.save(operation);
        return operation;
    }

    @GetMapping("/{name}")
    public OperationModelDisplay getByName(@PathVariable String name){
        return repository.findByName(name);
    }
}

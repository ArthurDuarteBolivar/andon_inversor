package com.api.nodemcu.repository;

import com.api.nodemcu.model.OperationModelDisplay;
import com.api.nodemcu.model.OperationModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepositoryDisplay extends JpaRepository<OperationModelDisplay, Integer> {
    List<OperationModelDisplay> findAll();

    OperationModelDisplay findByName(String name);

    <OperationMod extends OperationModelDisplay> OperationMod save(OperationMod nodemcu);
}

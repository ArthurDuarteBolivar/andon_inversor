package com.api.nodemcu.repository;

import com.api.nodemcu.model.NodemcuModel;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.model.OperationModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepositoryProvedor extends JpaRepository<OperationModelProvedor, Integer> {
    List<OperationModelProvedor> findAll();

    OperationModelProvedor findByName(String name);

    <OperationMod extends OperationModelProvedor> OperationMod save(OperationMod nodemcu);
}

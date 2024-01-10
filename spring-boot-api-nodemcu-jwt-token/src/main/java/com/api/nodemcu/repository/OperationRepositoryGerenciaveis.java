package com.api.nodemcu.repository;

import com.api.nodemcu.model.NodemcuModel;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepositoryGerenciaveis extends JpaRepository<OperationModelGerenciaveis, Integer> {
    List<OperationModelGerenciaveis> findAll();

    OperationModelGerenciaveis findByName(String name);

    <OperationMod extends OperationModelGerenciaveis> OperationMod save(OperationMod nodemcu);
}

package com.api.nodemcu.repository;

import com.api.nodemcu.model.NodemcuModel;
import com.api.nodemcu.model.NodemcuModelGerenciaveis;
import com.api.nodemcu.model.OperationModel;
import com.api.nodemcu.model.OperationModelGerenciaveis;
import jakarta.transaction.Transactional;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@EnableJpaRepositories
public interface NodemcuRepositoryGerenciaveis extends JpaRepository<NodemcuModelGerenciaveis, Integer> {

    List<NodemcuModelGerenciaveis> findAll();

    NodemcuModelGerenciaveis findByNameId(OperationModelGerenciaveis nameId);

    @Modifying
    @Query(value = "UPDATE thdados_gerenciaveis n SET n.localTC = :newLocalTC WHERE n.nameId = :nameId", nativeQuery = true)
    void updateLocalTCByNameId(@Param("newLocalTC") Integer newLocalTC, @Param("nameId") Integer nameId);


    <NodemcuMod extends NodemcuModelGerenciaveis> NodemcuMod save(NodemcuMod nodemcu);

}

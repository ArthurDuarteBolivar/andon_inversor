package com.api.nodemcu.repository;

import com.api.nodemcu.model.*;
import jakarta.transaction.Transactional;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@EnableJpaRepositories
public interface NodemcuRepositoryProvedor extends JpaRepository<NodemcuModelProvedor, Integer> {

    List<NodemcuModelProvedor> findAll();

    NodemcuModelProvedor findByNameId(OperationModelProvedor nameId);

    @Modifying
    @Query(value = "UPDATE thdados_provedor n SET n.localTC = :newLocalTC WHERE n.nameId = :nameId", nativeQuery = true)
    void updateLocalTCByNameId(@Param("newLocalTC") Integer newLocalTC, @Param("nameId") Integer nameId);


    <NodemcuMod extends NodemcuModelProvedor> NodemcuMod save(NodemcuMod nodemcu);

}

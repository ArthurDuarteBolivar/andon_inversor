package com.api.nodemcu.repository;

import com.api.nodemcu.model.NodemcuModelDisplay;
import com.api.nodemcu.model.NodemcuModelProvedor;
import com.api.nodemcu.model.OperationModelDisplay;
import com.api.nodemcu.model.OperationModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@EnableJpaRepositories
public interface NodemcuRepositoryDisplay extends JpaRepository<NodemcuModelDisplay, Integer> {

    List<NodemcuModelDisplay> findAll();

    NodemcuModelDisplay findByNameId(OperationModelDisplay nameId);

    @Modifying
    @Query(value = "UPDATE thdados_display n SET n.localTC = :newLocalTC WHERE n.nameId = :nameId", nativeQuery = true)
    void updateLocalTCByNameId(@Param("newLocalTC") Integer newLocalTC, @Param("nameId") Integer nameId);


    <NodemcuMod extends NodemcuModelDisplay> NodemcuMod save(NodemcuMod nodemcu);

}

package com.api.nodemcu.repository;

import com.api.nodemcu.model.OperationModel;
import com.api.nodemcu.model.RealizadoHorariaModel;
import com.api.nodemcu.model.RealizadoHorariaModelGerenciaveis;

import com.api.nodemcu.model.RealizadoHorariaModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RealizadoHorariaRepositoryProvedor extends JpaRepository<RealizadoHorariaModelProvedor, Integer> {
    List<RealizadoHorariaModelProvedor> findAll();

    @Query(value = "SELECT SUM(horas10 + horas11 + horas12 + horas13 + horas14 + horas15 + horas16 + horas17 + horas7 + horas8 + horas9) AS total_soma FROM realizadohoraria;", nativeQuery = true)
    Integer somarTudo();


    <RealizadoHorariaMod extends RealizadoHorariaModelProvedor> RealizadoHorariaMod save(RealizadoHorariaMod nodemcu);
}

package com.api.nodemcu.repository;

import com.api.nodemcu.model.RealizadoHorariaModelDisplay;
import com.api.nodemcu.model.RealizadoHorariaModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RealizadoHorariaRepositoryDisplay extends JpaRepository<RealizadoHorariaModelDisplay, Integer> {
    List<RealizadoHorariaModelDisplay> findAll();

    @Query(value = "SELECT SUM(horas10 + horas11 + horas12 + horas13 + horas14 + horas15 + horas16 + horas17 + horas7 + horas8 + horas9) AS total_soma FROM realizadohoraria;", nativeQuery = true)
    Integer somarTudo();


    <RealizadoHorariaMod extends RealizadoHorariaModelDisplay> RealizadoHorariaMod save(RealizadoHorariaMod nodemcu);
}

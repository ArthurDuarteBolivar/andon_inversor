package com.api.nodemcu.repository;

import com.api.nodemcu.model.MainModelGerenciaveis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainRepositoryGerenciaveis extends JpaRepository<MainModelGerenciaveis, Integer> {

    List<MainModelGerenciaveis> findAll();

    Optional<MainModelGerenciaveis> findById(Integer id);

    <MainMod extends MainModelGerenciaveis> MainMod save(MainMod nodemcu);

}

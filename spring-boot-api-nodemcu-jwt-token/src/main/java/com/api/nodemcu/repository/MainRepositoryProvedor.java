package com.api.nodemcu.repository;

import com.api.nodemcu.model.MainModelGerenciaveis;

import com.api.nodemcu.model.MainModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainRepositoryProvedor extends JpaRepository<MainModelProvedor, Integer> {

    List<MainModelProvedor> findAll();

    Optional<MainModelProvedor> findById(Integer id);

    <MainMod extends MainModelProvedor> MainMod save(MainMod nodemcu);

}

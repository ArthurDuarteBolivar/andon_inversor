package com.api.nodemcu.repository;

import com.api.nodemcu.model.MainModelDisplay;
import com.api.nodemcu.model.MainModelProvedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainRepositoryDisplay extends JpaRepository<MainModelDisplay, Integer> {

    List<MainModelDisplay> findAll();

    Optional<MainModelDisplay> findById(Integer id);

    <MainMod extends MainModelDisplay> MainMod save(MainMod nodemcu);

}

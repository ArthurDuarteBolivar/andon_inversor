package com.api.nodemcu.controllers.provedor;

import com.api.nodemcu.model.ImpostoDTO;
import com.api.nodemcu.model.MainModelGerenciaveis;
import com.api.nodemcu.model.MainModelProvedor;
import com.api.nodemcu.repository.MainRepositoryGerenciaveis;
import com.api.nodemcu.repository.MainRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main_provedor")
public class MainControllerProvedor {

    @Autowired
    MainRepositoryProvedor repostory;

    @GetMapping()
    public List<MainModelProvedor> listAll(){
        return repostory.findAll();
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Integer id, @RequestBody ImpostoDTO dto) {
        MainModelProvedor main = repostory.findById(id).get();
        main.setImposto(dto.getImposto());
        main.setTCimposto(dto.getTCimposto());
        main.setShiftTime(dto.getShiftTime());
        repostory.save(main);
    }




}

package com.api.nodemcu.controllers.display;

import com.api.nodemcu.model.ImpostoDTO;
import com.api.nodemcu.model.MainModelDisplay;
import com.api.nodemcu.model.MainModelProvedor;
import com.api.nodemcu.repository.MainRepositoryDisplay;
import com.api.nodemcu.repository.MainRepositoryProvedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main_display")
public class MainControllerDisplay {

    @Autowired
    MainRepositoryDisplay repostory;

    @GetMapping()
    public List<MainModelDisplay> listAll(){
        return repostory.findAll();
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Integer id, @RequestBody ImpostoDTO dto) {
        MainModelDisplay main = repostory.findById(id).get();
        main.setImposto(dto.getImposto());
        main.setTCimposto(dto.getTCimposto());
        main.setShiftTime(dto.getShiftTime());
        repostory.save(main);
    }




}

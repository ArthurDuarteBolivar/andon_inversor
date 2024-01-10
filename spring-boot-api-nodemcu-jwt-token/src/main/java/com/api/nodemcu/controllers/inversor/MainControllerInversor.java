package com.api.nodemcu.controllers.inversor;

import com.api.nodemcu.model.ImpostoDTO;
import com.api.nodemcu.model.MainModel;
import com.api.nodemcu.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
public class MainControllerInversor {

    @Autowired
    MainRepository repostory;

    @GetMapping()
    public List<MainModel> listAll(){
        return repostory.findAll();
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Integer id, @RequestBody ImpostoDTO dto) {
        MainModel main = repostory.findById(id).get();
        main.setImposto(dto.getImposto());
        main.setTCimposto(dto.getTCimposto());
        main.setShiftTime(dto.getShiftTime());
        repostory.save(main);
    }




}

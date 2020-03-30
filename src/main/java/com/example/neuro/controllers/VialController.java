package com.example.neuro.controllers;


import com.example.neuro.beans.Vial;
import com.example.neuro.service.VialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.soap.Addressing;
import java.util.List;

@RestController
@RequestMapping("vial/")
public class VialController {

    @Autowired
    private VialService vialService = new VialService();

    @GetMapping("/getAll")
    public List<Vial> getVials() {
        return vialService.getVialsRest();
    }

    @GetMapping("/getOne")
    public Vial getVial(@RequestParam Integer id) {
        return vialService.getVialRest(id);
    }

    @PostMapping("/insert")
    public Vial addVial(@Valid @RequestBody Vial vial) {
        return vialService.addVialRest(vial);
    }

//    @PostMapping("/insert")
//    public Vial addVial(@Valid @RequestBody Vial vial, @RequestParam Integer mId, @RequestParam Integer tId) {
//        return vialService.addVialRest(vial, mId, tId);
//    }

}

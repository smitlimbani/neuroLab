package com.example.neuro.controllers;


import com.example.neuro.beans.Vial;
import com.example.neuro.services.VialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/updateVial")
    public Vial  updateVial(@RequestBody @Valid Vial vial) {
        return vialService.updateVialRest(vial);
    }

    @PostMapping("/updateVials")
    public String  updateVialsRest(@RequestBody @Valid List<Vial> vials) {
        return vialService.updateVialsRest(vials);
    }

    @PostMapping("/delete")
    public void deleteVial(@RequestBody @Valid Integer id){vialService.deleteVialByIdRest(id);}

//    @PostMapping("/updateVials")
//    public String updateVials(@RequestBody @Valid String jsonString) throws JsonProcessingException {
//        return vialService.updateVialsRest(jsonString);
//    }

//    @PostMapping("/insert")
//    public Vial addVial(@Valid @RequestBody Vial vial, @RequestParam Integer mId, @RequestParam Integer tId) {
//        return vialService.addVialRest(vial, mId, tId);
//    }

}

package com.taass.salon_service.controller;

import com.taass.salon_service.data.SalonDTO;
import com.taass.salon_service.model.Salon;
import com.taass.salon_service.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
public class SalonController {
    private final SalonService salonService;

    @Autowired
    public SalonController(SalonService salonService) {this.salonService=salonService;}

    @PostMapping
    public Salon addSalon(@RequestBody SalonDTO salon) {
        return salonService.addSalon(salon);
    }

    @GetMapping("/{id}")
    public SalonDTO getSalonById(@PathVariable Long id) {
        return  salonService.getSalonById(id);
    }

    @GetMapping
    public List<SalonDTO> getAllSalons() {
        return salonService.getAllSalons();
    }
}

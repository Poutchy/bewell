package com.taass.salon_service.controller;

import com.taass.salon_service.data.SalonDTO;
import com.taass.salon_service.model.Salon;
import com.taass.salon_service.service.SalonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @PostMapping
    public ResponseEntity<Salon> addSalon(@RequestBody SalonDTO salonDTO) {
        Salon createdSalon = salonService.addSalon(salonDTO);
        return new ResponseEntity<>(createdSalon, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long id) {
        SalonDTO salonDTO = salonService.getSalonById(id);
        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getAllSalons() {
        List<SalonDTO> salons = salonService.getAllSalons();
        return ResponseEntity.ok(salons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salon> updateSalon(@PathVariable Long id, @RequestBody SalonDTO updatedSalonDTO) {
        Salon updatedSalon = salonService.updateSalon(id, updatedSalonDTO);
        return ResponseEntity.ok(updatedSalon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalonById(@PathVariable Long id) {
        salonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
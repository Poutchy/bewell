package com.taass.salon_service.controller;

import com.taass.salon_service.data.ServiceDTO;
import com.taass.salon_service.model.Service;
import com.taass.salon_service.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody ServiceDTO serviceDTO) {
        Service createdService = serviceService.addService(serviceDTO);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(
                serviceService.getServiceById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return ResponseEntity.ok(
                serviceService.getAllServices()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody ServiceDTO updatedServiceDTO) {
        Service updatedService = serviceService.updateService(id, updatedServiceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable Long id) {
        serviceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
package com.taass.salon_service.service;

import com.taass.salon_service.data.SalonDTO;
import com.taass.salon_service.data.SalonMapper;
import com.taass.salon_service.data.ServiceDTO;
import com.taass.salon_service.exception.SalonNotFoundException;
import com.taass.salon_service.model.Salon;
import com.taass.salon_service.repository.SalonRepository;
import com.taass.salon_service.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SalonService {

    private final SalonRepository salonRepository;
    private final ServiceRepository serviceRepository;
    private final SalonMapper salonMapper;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public SalonService(SalonRepository salonRepository, ServiceRepository serviceRepository, SalonMapper salonMapper) {
        this.salonRepository = salonRepository;
        this.serviceRepository = serviceRepository;
        this.salonMapper = salonMapper;
    }

    public Salon addSalon(SalonDTO salonDTO) {

        logger.info("Adding salon: " + salonDTO);

        validateSalonDTO(salonDTO);

        List<com.taass.salon_service.model.Service> services = new ArrayList<>();

        if (salonDTO.getServices() != null && !salonDTO.getServices().isEmpty()) {
            for (ServiceDTO serviceDTO : salonDTO.getServices()) {
                if (serviceDTO != null) {
                    com.taass.salon_service.model.Service service = serviceRepository.findById(serviceDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Service with ID " + serviceDTO.getId() + " not found"));
                    salonRepository.createOffersRelationship(salonDTO.getId(), service.getId());
                    services.add(service);
                } else {
                    throw new IllegalArgumentException("Service ID is required for existing services");
                }
            }
        }

        Salon salon = salonMapper.toEntity(salonDTO);
        salon.setServices(services);
        return salonRepository.save(salon);
    }

    public SalonDTO getSalonById(Long id) {
        logger.info("Fetching salon by id: "+ id);
        Salon salon = salonRepository.findById(id)
                .orElseThrow(() -> new SalonNotFoundException(id.toString()));
        return salonMapper.toDTO(salon);
    }

    public List<SalonDTO> getAllSalons() {
        logger.info("Fetching all salons");
        return salonRepository.findAll()
                .stream()
                .map(salonMapper::toDTO)
                .toList();
    }

    @Transactional
    public Salon updateSalon(Long id, SalonDTO updatedSalonDTO) {
        logger.info("Updating salon with id: " + id);
        validateSalonDTO(updatedSalonDTO);

        Salon existingSalon = salonRepository.findById(id)
                .orElseThrow(() -> new SalonNotFoundException("Salon not found with id: " + id));

        Salon salon = salonMapper.toEntity(updatedSalonDTO);

        List<com.taass.salon_service.model.Service> services = new ArrayList<>();

        if (updatedSalonDTO.getServices() != null && !updatedSalonDTO.getServices().isEmpty()) {
            for (ServiceDTO serviceDTO : updatedSalonDTO.getServices()) {
                if (serviceDTO != null) {
                    com.taass.salon_service.model.Service service = serviceRepository.findById(serviceDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Service with ID " + serviceDTO.getId() + " not found"));
                    // Create or ensure the relationship exists
                    salonRepository.createOffersRelationship(salon.getId(), service.getId());
                    services.add(service);
                } else {
                    throw new IllegalArgumentException("Service ID is required for existing services");
                }
            }
        }

        existingSalon.setName(salon.getName());
        existingSalon.setDescription(salon.getDescription());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setPhone(salon.getPhone());
        existingSalon.setEmail(salon.getEmail());
        existingSalon.setPricingRange(salon.getPricingRange());
        existingSalon.setOpeningHours(salon.getOpeningHours());
        existingSalon.setLatitude(salon.getLatitude());
        existingSalon.setLongitude(salon.getLongitude());
        existingSalon.setServices(salon.getServices());
        existingSalon.setWebsite(salon.getWebsite());
        existingSalon.setSocialMediaLinks(salon.getSocialMediaLinks());
        existingSalon.setImageUrls(salon.getImageUrls());
        existingSalon.setServices(services);

        return salonRepository.save(existingSalon);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting salon by id: " + id);
        if (!salonRepository.existsById(id)) {
            throw new SalonNotFoundException("Salon not found with id: " + id);
        }
        salonRepository.deleteById(id);
    }

    private void validateSalonDTO(SalonDTO salonDTO) {
        if (salonDTO.getName() == null || salonDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Salon name must not be empty");
        }
        if (salonDTO.getAddress() == null || salonDTO.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Salon address must not be empty");
        }
        if (salonDTO.getPhone() == null || salonDTO.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Salon phone must not be empty");
        }
        if (salonDTO.getEmail() == null || salonDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Salon email must not be empty");
        }
        if (salonDTO.getLatitude() == null || salonDTO.getLongitude() == null) {
            throw new IllegalArgumentException("Salon location (latitude and longitude) must not be null");
        }
    }
}
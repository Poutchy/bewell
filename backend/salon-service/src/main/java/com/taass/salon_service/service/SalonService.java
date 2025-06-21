package com.taass.salon_service.service;

import com.taass.salon_service.data.SalonDTO;
import com.taass.salon_service.data.SalonMapper;
import com.taass.salon_service.data.ServiceDTO;
import com.taass.salon_service.exception.SalonNotFoundException;
import com.taass.salon_service.model.Salon;
import com.taass.salon_service.model.TimeSlot;
import com.taass.salon_service.repository.SalonRepository;
import com.taass.salon_service.repository.ServiceRepository;
import com.taass.salon_service.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SalonService {

    private final SalonRepository salonRepository;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final SalonMapper salonMapper;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public SalonService(SalonRepository salonRepository, ServiceRepository serviceRepository,TimeSlotRepository timeSlotRepository, SalonMapper salonMapper) {
        this.salonRepository = salonRepository;
        this.serviceRepository = serviceRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.salonMapper = salonMapper;
    }

    public Salon addSalon(SalonDTO salonDTO) {

        logger.info("Adding salon: " + salonDTO);

        validateSalonDTO(salonDTO);

        List<com.taass.salon_service.model.Service> services = processServices(salonDTO.getServices(), salonDTO.getId());
        List<TimeSlot> timeSlots = processTimeSlots(salonDTO.getTimeSlots(), salonDTO.getId());

        Salon salon = salonMapper.toEntity(salonDTO);
        salon.setServices(services);
        salon.setTimeSlots(timeSlots);
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

        List<com.taass.salon_service.model.Service> services = processServices(updatedSalonDTO.getServices(), updatedSalonDTO.getId());
        List<TimeSlot> timeSlots = processTimeSlots(updatedSalonDTO.getTimeSlots(), updatedSalonDTO.getId());

        updateSalonFields(existingSalon, salon);
        existingSalon.setServices(services);
        existingSalon.setTimeSlots(timeSlots);

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

    private void updateSalonFields(Salon existingSalon, Salon updatedSalon) {
        existingSalon.setName(updatedSalon.getName());
        existingSalon.setDescription(updatedSalon.getDescription());
        existingSalon.setAddress(updatedSalon.getAddress());
        existingSalon.setPhone(updatedSalon.getPhone());
        existingSalon.setEmail(updatedSalon.getEmail());
        existingSalon.setPricingRange(updatedSalon.getPricingRange());
        existingSalon.setOpeningHours(updatedSalon.getOpeningHours());
        existingSalon.setLatitude(updatedSalon.getLatitude());
        existingSalon.setLongitude(updatedSalon.getLongitude());
        existingSalon.setWebsite(updatedSalon.getWebsite());
        existingSalon.setSocialMediaLinks(updatedSalon.getSocialMediaLinks());
        existingSalon.setImageUrls(updatedSalon.getImageUrls());
    }

    private List<com.taass.salon_service.model.Service> processServices(List<ServiceDTO> serviceDTOs, Long salonId) {
        List<com.taass.salon_service.model.Service> services = new ArrayList<>();
        if (serviceDTOs != null && !serviceDTOs.isEmpty()) {
            for (ServiceDTO serviceDTO : serviceDTOs) {
                if (serviceDTO != null) {
                    com.taass.salon_service.model.Service service = serviceRepository.findById(serviceDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Service with ID " + serviceDTO.getId() + " not found"));
                    salonRepository.createOffersRelationship(salonId, service.getId());
                    services.add(service);
                } else {
                    throw new IllegalArgumentException("Service ID is required for existing services");
                }
            }
        }
        return services;
    }

    private List<TimeSlot> processTimeSlots(List<TimeSlot> timeSlotDTOs, Long salonId) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        if (timeSlotDTOs != null && !timeSlotDTOs.isEmpty()) {
            for (TimeSlot timeSlotDTO : timeSlotDTOs) {
                if (timeSlotDTO != null) {
                    TimeSlot savedTimeSlot = timeSlotRepository.findById(timeSlotDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Time slot with ID " + timeSlotDTO.getId() + " not found"));
                    salonRepository.createHasTimeSlotRelationship(salonId, savedTimeSlot.getId());
                    timeSlots.add(savedTimeSlot);
                } else {
                    throw new IllegalArgumentException("TimeSlot details are required for adding TimeSlots");
                }
            }
        }
        return timeSlots;
    }
}
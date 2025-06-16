package com.taass.salon_service.service;

import com.taass.salon_service.data.SalonDTO;
import com.taass.salon_service.data.SalonMapper;
import com.taass.salon_service.model.Salon;
import com.taass.salon_service.repository.SalonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
@Data
public class SalonService {

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;
    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public SalonService(SalonRepository salonRepository, SalonMapper salonMapper) {

        this.salonRepository = salonRepository;
        this.salonMapper = salonMapper;
    }

    public Salon addSalon(SalonDTO salon) {
        logger.info("Adding salon " + salon);
        return salonRepository.save(salonMapper.toEntity(salon));
    }

    public SalonDTO getSalonById(Long id) {
        logger.info("Fetching salon by {id:" + id + "}");
        Salon salon = salonRepository.getSalonById(id);
        return salonMapper.toDTO(salon);
    }

    public List<SalonDTO> getAllSalons() {
        logger.info("Fetching all salons");
        List<Salon> salons = salonRepository.findAll();
        return salons.stream()
                .map(salonMapper::toDTO)
                .toList();
    }
}

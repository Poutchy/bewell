package com.taass.salon_service.service;

import com.taass.salon_service.data.ServiceDTO;
import com.taass.salon_service.data.ServiceMapper;
import com.taass.salon_service.data.TagDTO;
import com.taass.salon_service.exception.ServiceNotFoundException;
import com.taass.salon_service.model.Tag;
import com.taass.salon_service.repository.ServiceRepository;
import com.taass.salon_service.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final TagRepository tagRepository;
    private final ServiceMapper serviceMapper;
    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public ServiceService(ServiceRepository serviceRepository, TagRepository tagRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.tagRepository = tagRepository;
        this.serviceMapper = serviceMapper;
    }

    public com.taass.salon_service.model.Service addService(ServiceDTO serviceDTO) {

        logger.info("Adding service: " + serviceDTO);

        validateServiceDTO(serviceDTO);

        List<Tag> tags = new ArrayList<>();

        if (serviceDTO.getTags() != null && !serviceDTO.getTags().isEmpty()) {
            for (TagDTO tagDTO : serviceDTO.getTags()) {
                if (tagDTO != null) {
                    Tag tag = tagRepository.findById(tagDTO.getId())
                            .orElseThrow(() -> new ServiceNotFoundException(tagDTO.getId().toString()));
                    serviceRepository.createHasTagRelationship(serviceDTO.getId(), tag.getId());
                    tags.add(tag);
                } else {
                    throw new IllegalArgumentException("Tag ID is required for existing tags");
                }
            }
        }

        com.taass.salon_service.model.Service service = serviceMapper.toEntity(serviceDTO);
        service.setTags(tags);
        return serviceRepository.save(service);
    }

    public ServiceDTO getServiceById(Long id) {
        logger.info("Fetching service by {id:" + id + "}");
        com.taass.salon_service.model.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id.toString()));
        return serviceMapper.toDTO(service);
    }

    public List<ServiceDTO> getAllServices() {
        logger.info("Fetching all services");
        List<com.taass.salon_service.model.Service> services = serviceRepository.findAll();
        return services.stream()
                .map(serviceMapper::toDTO)
                .toList();
    }

    @Transactional
    public com.taass.salon_service.model.Service updateService(Long id, ServiceDTO updatedServiceDTO) {
        logger.info("Updating service " + updatedServiceDTO);
        validateServiceDTO(updatedServiceDTO);

        com.taass.salon_service.model.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id.toString()));

        com.taass.salon_service.model.Service serviceDTO = serviceMapper.toEntity(updatedServiceDTO);

        existingService.setName(serviceDTO.getName());
        existingService.setDescription(serviceDTO.getDescription());
        existingService.setPrice(serviceDTO.getPrice());
        existingService.setDuration(serviceDTO.getDuration());
        existingService.setAvailable(serviceDTO.isAvailable());
        existingService.setDiscount(serviceDTO.getDiscount());
        existingService.setTags(serviceDTO.getTags());

        return serviceRepository.save(existingService);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting service by {id:" + id + "}");
        if (!serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException(id.toString());
        }
        serviceRepository.deleteById(id);
    }

    private void validateServiceDTO(ServiceDTO serviceDTO) {
        if (serviceDTO.getName() == null || serviceDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Service name must not be empty");
        }
        if (serviceDTO.getDescription() == null || serviceDTO.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Service description must not be empty");
        }
        if (serviceDTO.getPrice() == null || serviceDTO.getPrice() < 0) {
            throw new IllegalArgumentException("Service price must be non-negative");
        }
        if (serviceDTO.getDuration() == null) {
            throw new IllegalArgumentException("Service duration must not be null");
        }
        // isAvailable and discount are optional; validate discount only if present
        if (serviceDTO.getDiscount() != null && (serviceDTO.getDiscount() < 0 || serviceDTO.getDiscount() > 100)) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
    }
}


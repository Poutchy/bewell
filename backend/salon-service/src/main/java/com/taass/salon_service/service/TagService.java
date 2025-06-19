package com.taass.salon_service.service;

import com.taass.salon_service.exception.TagNotFoundException;
import com.taass.salon_service.model.Tag;
import com.taass.salon_service.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Transactional
    public Tag updateTag(Long id, Tag updatedTag) {
        return tagRepository.findById(id).map(tag -> {
            tag.setTagName(updatedTag.getTagName());
            return tagRepository.save(tag);
        }).orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));
    }

    @Transactional
    public void deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            throw new TagNotFoundException("Tag not found with id: " + id);
        }
    }
}

package com.taass.salon_service.controller;

import com.taass.salon_service.exception.TagNotFoundException;
import com.taass.salon_service.model.Tag;
import com.taass.salon_service.service.TagService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.addTag(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            throw new TagNotFoundException("Tag not found with id: " + id);
        }
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag updatedTag) {
        Tag tag = tagService.updateTag(id, updatedTag);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
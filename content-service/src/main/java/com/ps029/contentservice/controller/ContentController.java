
package com.ps029.contentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import com.ps029.contentservice.entity.Content;
import com.ps029.contentservice.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Content> createContent(
            @RequestBody Content content) {

        Content savedContent =
                contentService.createContent(content);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedContent);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Content>> getAllContents() {

        return ResponseEntity.ok(
                contentService.getAllContents());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                contentService.getContentById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(
            @PathVariable Long id,
            @RequestBody Content content) {

        return ResponseEntity.ok(
                contentService.updateContent(id, content));
    }
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/instance")
    public String getInstance() {
        return "Request handled by Content Service instance on port: "
                + serverPort;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(
            @PathVariable Long id) {

        contentService.deleteContent(id);

        return ResponseEntity.noContent().build();
    }
}

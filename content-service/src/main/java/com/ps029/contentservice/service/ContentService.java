
package com.ps029.contentservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ps029.contentservice.entity.Content;
import com.ps029.contentservice.repository.ContentRepository;

@Service
public class ContentService {

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    // CREATE
    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    // READ ALL
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    // READ BY ID
    public Content getContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Content not found with ID: " + id));
    }

    // UPDATE
    public Content updateContent(Long id, Content newContent) {

        Content existingContent = getContentById(id);

        existingContent.setTitle(newContent.getTitle());
        existingContent.setAuthor(newContent.getAuthor());
        existingContent.setContentType(newContent.getContentType());
        existingContent.setDescription(newContent.getDescription());
        existingContent.setPublicationYear(
                newContent.getPublicationYear());

        return contentRepository.save(existingContent);
    }

    // DELETE
    public void deleteContent(Long id) {

        Content existingContent = getContentById(id);

        contentRepository.delete(existingContent);
    }
}

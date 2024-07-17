package com.appswave.task.controller;

import com.appswave.task.model.dtos.NewsDTO;
import com.appswave.task.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/approved")
    public ResponseEntity<List<NewsDTO>> getApprovedNews() {
        return new ResponseEntity<>(newsService.getApprovedNews(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTENT_WRITER')")
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('CONTENT_WRITER')")
    @DeleteMapping("/writer-delete/{id}")
    public ResponseEntity deleteNewsByContentWriter(@PathVariable Long id) {
        return newsService.deleteNewsByContentWriter(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.getNewsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO news) {
        return new ResponseEntity<>(newsService.createNews(news), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<NewsDTO> approveNews(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.approveNews(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody NewsDTO news) {
        return new ResponseEntity<>(newsService.updateNews(id, news), HttpStatus.OK);
    }
}


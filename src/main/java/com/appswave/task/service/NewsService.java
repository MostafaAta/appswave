package com.appswave.task.service;

import com.appswave.task.common.constants.Constants;
import com.appswave.task.mapper.NewsMapper;
import com.appswave.task.model.dtos.NewsDTO;
import com.appswave.task.model.entity.News;
import com.appswave.task.model.enums.NewsStatus;
import com.appswave.task.model.repos.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public List<NewsDTO> getAllNews() {
        return newsMapper.toListDTO(newsRepository.findAll());
    }

    public NewsDTO getNewsById(Long id) {
        return newsMapper.toDTO(newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id)));
    }

    public NewsDTO createNews(NewsDTO news) {
        news.setStatus(NewsStatus.PENDING);
        news.setCreatedAt(LocalDateTime.now());
        return newsMapper.toDTO(newsRepository.save(newsMapper.toEntity(news)));
    }

    public NewsDTO updateNews(Long id, NewsDTO news) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.ENTITY_NOT_FOUND));
        existingNews.setTitle(news.getTitle());
        existingNews.setTitleAr(news.getTitleAr());
        existingNews.setDescription(news.getDescription());
        existingNews.setDescriptionAr(news.getDescriptionAr());
        existingNews.setPublishDate(news.getPublishDate());
        existingNews.setImageUrl(news.getImageUrl());
        return newsMapper.toDTO(newsRepository.save(existingNews));
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public NewsDTO approveNews(Long id) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.ENTITY_NOT_FOUND));
        existingNews.setStatus(NewsStatus.APPROVED);
        return newsMapper.toDTO(newsRepository.save(existingNews));
    }

    public List<NewsDTO> getApprovedNews() {
        return newsMapper.toListDTO(newsRepository.findByStatus(NewsStatus.APPROVED));
    }

    public ResponseEntity deleteNewsByContentWriter(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        if (news.getStatus() == NewsStatus.PENDING) {
            newsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Deleting approved news requires admin approval", HttpStatus.FORBIDDEN);
        }
    }
}

package com.appswave.task.model.repos;

import com.appswave.task.model.entity.News;
import com.appswave.task.model.enums.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByStatus(NewsStatus status);

    List<News> findByStatusAndPublishDateLessThan(NewsStatus newsStatus, LocalDate today);
}
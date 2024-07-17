package com.appswave.task.scheduler;

import com.appswave.task.model.entity.News;
import com.appswave.task.model.enums.NewsStatus;
import com.appswave.task.model.repos.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsSoftDeleteScheduler {

    private final NewsRepository newsRepository;

    @Scheduled(cron = "0 0 0 * * *") // Executes at midnight every day
    public void softDeleteExpiredNews() {
        LocalDate today = LocalDate.now();
        List<News> expiredNews = newsRepository.findByStatusAndPublishDateLessThan(NewsStatus.APPROVED, today);
        expiredNews.forEach(news -> {
            news.setStatus(NewsStatus.DELETED);
            newsRepository.save(news);
        });
        System.out.println("Soft deleted " + expiredNews.size() + " expired news items.");
    }
}

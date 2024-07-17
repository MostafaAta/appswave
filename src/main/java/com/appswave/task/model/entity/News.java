package com.appswave.task.model.entity;

import com.appswave.task.model.enums.NewsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "news")
public class News extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String titleAr;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String descriptionAr;

    @Column(nullable = false)
    private LocalDate publishDate;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private NewsStatus status;

}
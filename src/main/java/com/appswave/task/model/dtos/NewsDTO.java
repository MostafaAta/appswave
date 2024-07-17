package com.appswave.task.model.dtos;

import com.appswave.task.model.enums.NewsStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsDTO extends BaseDTO {
    private String title;
    private String titleAr;
    private String description;
    private String descriptionAr;
    private LocalDate publishDate;
    private String imageUrl;
    private NewsStatus status;
}
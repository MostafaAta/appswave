package com.appswave.task.mapper;

import com.appswave.task.model.dtos.NewsDTO;
import com.appswave.task.model.entity.News;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO toDTO(News user);

    News toEntity(NewsDTO user);

    List<NewsDTO> toListDTO(List<News> users);
}

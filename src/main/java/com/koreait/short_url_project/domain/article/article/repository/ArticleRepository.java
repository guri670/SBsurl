package com.koreait.short_url_project.domain.article.article.repository;

import com.koreait.short_url_project.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByIdInOrderByTitleDescIdAsc(List<Long> ids);

    List<Article> findByTitleContaining(String title);

    List<Article> findByTitleAndBody(String title, String body);
}
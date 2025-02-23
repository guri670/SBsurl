package com.koreait.short_url_project.global.initData;

import com.koreait.short_url_project.domain.article.article.entity.Article;
import com.koreait.short_url_project.domain.article.article.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

//!prod == dev or test
    @Profile("!prod")
    @Configuration
    @RequiredArgsConstructor
    public class NotProd {
        @Lazy
        @Autowired
        private NotProd self;

        private final ArticleService articleService;

        @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체
        public ApplicationRunner initDataProd() {
            return args -> {
                self.work1();
                self.work2();
            };
        }

        @Transactional
        public void work1() {
            if (articleService.count() > 0) return;

            Article article1 = articleService.write("제목 1", "내용 1").getData();
            Article article2 = articleService.write("제목 2", "내용 2").getData();
            article2.setTitle("제목 2-2");
            articleService.delete(article1);
        }

        @Transactional
        public void work2() {
            Optional<Article> opArticle = articleService.findById(2L); // jpa 기본제공
            List<Article> articles = articleService.findAll(); // jpa 기본제공
        }
    }

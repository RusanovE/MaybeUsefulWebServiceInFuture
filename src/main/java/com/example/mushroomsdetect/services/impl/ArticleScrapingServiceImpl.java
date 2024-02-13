package com.example.mushroomsdetect.services.impl;

import com.example.mushroomsdetect.services.ArticleScrapingService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArticleScrapingServiceImpl implements ArticleScrapingService {

    private static final String INFO_SITE_URL = "https://www.unian.net/detail/main_news"; // Замените на URL вашего инфо сайта

    @Override
    public List<Article> scrapeArticleTitles() {
        List<Article> articleTitles = new ArrayList<>();

        try {
            Document document = Jsoup.connect(INFO_SITE_URL).get();
            Elements articleElements = document.select(".list-thumbs__item"); // Используйте ваш CSS-селектор

            for (Element articleElement : articleElements) {
                String url = articleElement.select(".list-thumbs__item a").attr("href");
                String imageUrl = articleElement.select(".list-thumbs__image img").attr("data-src");
                String title = articleElement.select(".list-thumbs__title").text();
                String time = articleElement.select(".list-thumbs__time").text();

                Article article = new Article(imageUrl, title, time, url);
                articleTitles.add(article);
            }
        } catch (IOException e) {
            log.error(e.getMessage()); // Обработка ошибок при web scraping
        }

        return articleTitles;
    }

    @Setter
    @Getter
    public static class Article {
        private String title;
        private String url;
        private String imageUrl;
        private String time;

        public Article(String imageUrl, String title, String time, String url) {
            this.imageUrl = imageUrl;
            this.title = title;
            this.time = time;
            this.url = url;
        }

        // Геттеры и сеттеры

    }
}

package com.example.mushroomsdetect.services;

import com.example.mushroomsdetect.services.impl.ArticleScrapingServiceImpl;

import java.util.List;

public interface ArticleScrapingService {

    public List<ArticleScrapingServiceImpl.Article> scrapeArticleTitles();
}

package com.example.mushroomsdetect.services.impl;

import com.example.mushroomsdetect.entitys.University;
import com.example.mushroomsdetect.services.UniversityScrapingService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UniversityScrapingServiceImpl implements UniversityScrapingService {

    private static final String INFO_SITE_URL = "https://ru.osvita.ua/vnz/guide/list/25"; // Замените на URL вашего инфо сайта

    @Override
    public List<University> scrapeUniversityTitles() {
        List<University> universities = new ArrayList<>();
            try {
                Document document = Jsoup.connect(INFO_SITE_URL).get();
                Elements universityElements = document.select(".block-frame-2047"); // Use the appropriate CSS selector

                universityElements.forEach(element -> {
                    String universityUrl = element.select(".rectangle-283 a").attr("href");
                    String imageUrl = element.select(".rectangle-283 img").attr("src");
                    String title = element.select(".block-frame-2089 a").text();
                    String place = element.select(".adress-educational").text();
                    University university = new University(title, universityUrl,imageUrl, place);
                    universities.add(university);
                });
            } catch (IOException e) {
                log.error(e.getMessage()); // Обработка ошибок при web scraping
            }
        return universities;
    }
}

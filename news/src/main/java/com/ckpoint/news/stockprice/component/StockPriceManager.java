package com.ckpoint.news.stockprice.component;

import com.ckpoint.news.news.domain.News;
import com.ckpoint.news.news.repository.NewsRepository;
import com.ckpoint.news.stockprice.findprice.NaverSearch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockPriceManager {
    private final @NonNull
    NaverSearch naverSearch;

    private final @NonNull
    NewsRepository newsRepository;

    public News initStartPrice(News news) {
        if (news.getCompany() == null || news.getCompany().length() < 1) {
            return news;
        }

        Long price = this.naverSearch.getCurrentPrice(news);
        news.setStartPrice(price);
        return this.newsRepository.save(news);
    }

    @Scheduled(cron = "0 30 15 * * *")
    private void initdayEndPrice() {

        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 12));
        List<News> newsList = this.newsRepository.findByStartPriceIsNotNullAndDayEndPriceIsNullAndCreatedAtAfter(date);

        newsList.forEach(news -> {
            Long price = this.naverSearch.getCurrentPrice(news);
            news.setDayEndPrice(price);
            this.newsRepository.save(news);
        });
    }

}

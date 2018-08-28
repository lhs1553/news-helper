package com.ckpoint.news.crawling.scheduler;

import com.ckpoint.news.crawling.CrawlingInterface;
import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.repository.NewsFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SchdulerTask {

    @Autowired
    private CrawlingInterface[] crawlingInterfaces;

    private final Map<CompanyType, CrawlingInterface> ciMap = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @PostConstruct
    private void initMap() {
        for (CrawlingInterface crawlingInterface : this.crawlingInterfaces) {
            this.ciMap.put(crawlingInterface.getMyCompanyType(), crawlingInterface);
        }
    }

    @Scheduled(cron = "* * * * * *")
    public void crawling() {

        for (CrawlingInterface crawlingInterface : this.crawlingInterfaces) {
            executorService.execute(() -> {
                crawlingInterface.findDefaultNews();
            });
        }
    }
}

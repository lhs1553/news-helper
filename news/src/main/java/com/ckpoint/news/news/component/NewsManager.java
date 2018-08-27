package com.ckpoint.news.news.component;

import com.ckpoint.news.crawling.ContentCrawlingInterface;
import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;
import com.ckpoint.news.news.domain.NewsFilter;
import com.ckpoint.news.news.repository.NewsFilterRepository;
import com.ckpoint.news.news.repository.NewsRepository;
import com.ckpoint.news.receiver.domain.Receiver;
import com.ckpoint.news.receiver.repository.ReceiverRepository;
import com.ckpoint.news.sms.SmsApi;
import com.ckpoint.news.sms.SmsMsg;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsManager {

    private final @NonNull
    NewsRepository newsRepository;

    private final @NonNull
    ReceiverRepository receiverRepository;

    private final @NonNull
    NewsFilterRepository newsFilterRepository;

    private final @NonNull
    SmsApi smsApi;

    private final @NonNull
    ContentCrawlingInterface[] contentCrawlingInterfaces;

    private final ExecutorService smsExecutor = Executors.newCachedThreadPool();

    private boolean isNotiNews(News news) {
        List<NewsFilter> newsFilters = this.newsFilterRepository.findAll();

        for (NewsFilter newsFilter : newsFilters) {
            if (newsFilter.getAuth() != null) {
                if (!news.getAuth().contains(newsFilter.getAuth())) {
                    continue;
                } else {
                    return true;
                }
            }

            if (newsFilter.getKeyword() != null) {
                if (news.getTitle().contains(newsFilter.getKeyword())) {
                    return true;
                } else if (news.getContent().contains(newsFilter.getKeyword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void newsPush(String newsId, CompanyType companyType, String title, String auth, String url) {

        List<News> newsList = this.newsRepository.findByNewsIdAndCompanyType(newsId, companyType);
        if (!newsList.isEmpty()) {
            return;
        }

        News news = new News();
        news.setAuth(auth);
        news.setTitle(title);
        news.setUrl(url);
        news.setCompanyType(companyType);
        news.setNewsId(newsId);

        news = this.newsRepository.save(news);

        if (!StringUtils.isEmpty(news.getUrl())) {
            ContentCrawlingInterface contentFinder = Arrays.stream(contentCrawlingInterfaces).filter(ci -> ci.getMyCompanyType().equals(companyType)).findFirst().orElse(null);

            if (contentFinder != null) {
                try {
                    news = contentFinder.setContent(news);
                } catch (IOException e) {
                    log.info("content finder error : " + e + " url : " + news.getUrl());
                }
            }
            news = this.newsRepository.save(news);

            this.notiNews(news);
        }
    }

    private void notiNews(News news) {
        if (this.isNotiNews(news)) {
            this.broadCastSms(news.getHeadInfo());
            this.broadCastSms(news.getTitle());
            this.broadCastSms(news.getUrl());
        }
    }

    private void broadCastSms(String message) {
        for (Receiver receiver : this.receiverRepository.findAll()) {
            SmsMsg smsMsg = new SmsMsg();
            if (StringUtils.isEmpty(receiver.getSendPhone())) {
                receiver.setSendPhone("01040652126");
                receiver = this.receiverRepository.save(receiver);
            }

            smsMsg.setSendNumber(receiver.getSendPhone());
            smsMsg.setRecvNumber(receiver.getPhone());
            smsMsg.setMessage(message);

            this.smsExecutor.execute(() -> {
                this.smsApi.sendSms(smsMsg);
            });
        }
    }
}

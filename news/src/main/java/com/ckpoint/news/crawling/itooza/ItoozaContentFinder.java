package com.ckpoint.news.crawling.itooza;

import com.ckpoint.news.crawling.ContentCrawlingInterface;
import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ItoozaContentFinder implements ContentCrawlingInterface {

    @Override
    public News setContent(News news) throws IOException {

        Document docu = Jsoup.connect(news.getUrl()).get();
        Element ct = docu.getElementById("article-body");
        Element company = docu.getElementById("side-present");
        news.setCompany(company.text());
        news.setContent(ct.text());
        return news;
    }

    @Override
    public CompanyType getMyCompanyType() {
        return CompanyType.ITOOZA;
    }
}

package com.ckpoint.news.crawling.etoday;

import com.ckpoint.news.crawling.ContentCrawlingInterface;
import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class EtodayContentFinder implements ContentCrawlingInterface {


    @Override
    public News setContent(News news) throws IOException {
        Document docu = Jsoup.connect(news.getUrl()).get();

        Elements auth = docu.getElementsByClass("reporter_copy_w_1");
        if (auth != null && auth.size() > 0) {
            news.setAuth(auth.text());
        }
        Element content = docu.getElementById("newsContent");

        if (content != null) {
            news.setContent(content.text());
        }

        Elements stock = docu.getElementsByClass("e_CP_c");
        if (stock != null && stock.size() > 0) {
            news.setCompany(stock.text());
        }

        return news;
    }

    @Override
    public CompanyType getMyCompanyType() {
        return CompanyType.ETODAY;
    }
}

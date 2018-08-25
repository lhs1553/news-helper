package com.ckpoint.news.crawling.itooza;

import com.ckpoint.news.crawling.CrawlingInterface;
import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.component.NewsManager;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ItoozaFinder implements CrawlingInterface {


    @Autowired
    private NewsManager newsManager;

    private String baseUrl = "http://www.itooza.com/stock/stock_sub.htm?ss=10";

    private boolean isNumberValue(String str) {
        try {
            Long.valueOf(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void saveNews(Element tr) {


        Elements te = tr.getElementsByClass("t");
        String title = te.text();
        String url = te.select("a").toString();

        int lastIdx = url.indexOf("qSort=");
        if (lastIdx < 0) {
            lastIdx = url.lastIndexOf(";");
        }
        try {
            url = url.substring(url.indexOf("http"), lastIdx);
        } catch (Exception e) {
            log.info("url : " + url);
            log.info("url substring exception : " + e.getMessage());
        }

        String auth = tr.getElementsByClass("author").text();
        //String newsId = tr.getElementsByClass("a").text() + title;

        this.newsManager.newsPush(url, CompanyType.ITOOZA, title, auth, url);
    }

    @Override
    public void findDefaultNews() {

        Document docu = null;
        try {
            docu = Jsoup.connect(this.baseUrl).get();
        } catch (IOException e) {
            log.info("url : " + this.baseUrl + ", connect error : " + e.getMessage());
        }
        Elements elements = docu.select("tr");
        elements.stream()
                .filter(e -> e.getElementsByClass("a") != null)
                .filter(e -> this.isNumberValue(e.getElementsByClass("a").text()))
                .forEach(this::saveNews);

    }

    @Override
    public CompanyType getMyCompanyType() {
        return CompanyType.ITOOZA;
    }
}

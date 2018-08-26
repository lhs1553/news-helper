package com.ckpoint.news.crawling.etoday;

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
public class EtodayFinder implements CrawlingInterface {


    @Autowired
    private NewsManager newsManager;

    private String baseUrl = "http://www.etoday.co.kr/news/section/subsection.php?MID=2101";

    private void saveNews(Element tr) {

        if (tr.parent().className().indexOf("e_rightAticle_c") >= 0) {
            return;
        }

        String title = tr.getElementsByClass("tit").text();
        String url = tr.select("a").toString();
        String newsId = "";
        url = url.substring(url.indexOf("idxno=") + 6);
        newsId = url.substring(0, url.indexOf("\""));
        url = "http://www.etoday.co.kr/news/section/newsview.php?idxno=" + newsId;

        if (newsManager == null) {
            return;
        }

        this.newsManager.newsPush(newsId, CompanyType.ETODAY, title, null, url);
    }

    @Override
    public void findDefaultNews() {

        Document docu = null;
        try {
            docu = Jsoup.connect(this.baseUrl).get();
            Elements titles = docu.getElementsByClass("tit_area");

            titles.stream().forEach(this::saveNews);

        } catch (IOException e) {
            log.info("url : " + this.baseUrl + ", connect error : " + e.getMessage());
        }
    }

    @Override
    public CompanyType getMyCompanyType() {
        return CompanyType.ETODAY;
    }
}

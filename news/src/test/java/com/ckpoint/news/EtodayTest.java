package com.ckpoint.news;

import com.ckpoint.news.crawling.etoday.EtodayContentFinder;
import com.ckpoint.news.crawling.etoday.EtodayFinder;
import com.ckpoint.news.news.domain.News;
import org.junit.Test;

import java.io.IOException;

public class EtodayTest {

    @Test
    public void etodayListTest() {
        EtodayFinder etodayFinder = new EtodayFinder();
        etodayFinder.findDefaultNews();
    }

    @Test
    public void etodayContentTest() throws IOException {
        EtodayContentFinder etodayContentFinder = new EtodayContentFinder();
        News news = new News();
        news.setUrl("http://www.etoday.co.kr/news/section/newsview.php?idxno=1655876");
        news = etodayContentFinder.setContent(news);
    }
}

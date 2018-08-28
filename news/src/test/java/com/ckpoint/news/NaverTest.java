package com.ckpoint.news;

import com.ckpoint.news.news.domain.News;
import com.ckpoint.news.news.repository.NewsRepository;
import com.ckpoint.news.stockprice.component.StockPriceManager;
import com.ckpoint.news.stockprice.findprice.NaverSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverTest {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private StockPriceManager stockPriceManager;

    @Test
    public void testFindPrice() {
        List<News> news = this.newsRepository.findAll();
        news.stream().filter(n -> n.getStartPrice() == null).forEach(n ->{
            stockPriceManager.initStartPrice(n);
        });
    }
}

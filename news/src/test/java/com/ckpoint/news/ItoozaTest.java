package com.ckpoint.news;

import com.ckpoint.news.crawling.itooza.ItoozaContentFinder;
import com.ckpoint.news.crawling.itooza.ItoozaFinder;
import com.ckpoint.news.news.domain.News;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItoozaTest {

    @Autowired
    private ItoozaFinder itoozaFinder;

    @Test
    public void decodeTest() throws UnsupportedEncodingException {
        String encodeText = "%B0%ED%BB%F3%C7%D1";
        String decodeText = URLDecoder.decode(encodeText, "euc-kr");
        System.out.println(decodeText);
        String reEncodeText = URLEncoder.encode(decodeText, "euc-kr");
        System.out.println(reEncodeText);

        Assert.assertEquals(reEncodeText, encodeText);
    }

    @Test
    public void contentGetText() throws IOException {
        ItoozaContentFinder contentFinder = new ItoozaContentFinder();
        News news = new News();
        news.setUrl("http://www.itooza.com/common/iview.php?no=2018082107301511924&amp;smenu=100&amp;ss=99&amp;qSearch=pWriter&amp;qText=%B0%ED%BB%F3%C7%D1&amp;");
        news = contentFinder.setContent(news);
        Assert.assertNotNull(news.getContent());
    }


}

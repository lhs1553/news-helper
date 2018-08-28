package com.ckpoint.news.stockprice.findprice;

import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Component
public class NaverSearch {

    private final String baseGetStockIdUrl = "https://finance.naver.com/search/search.nhn?query=@A";
    private final String baseGetStockInfoUrl = "https://finance.naver.com/item/main.nhn?code=@A";

    private String getUrlStr(String str) {
        try {
            return URLEncoder.encode(str, "euc-kr");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    private String getStockId(String companyName) {
        String encodeName = this.getUrlStr(companyName);
        Document docu = null;
        try {
            docu = Jsoup.connect(baseGetStockIdUrl.replace("@A", encodeName)).get();
        } catch (IOException e) {
            log.info("jsoup io exception : " + companyName);
            return null;
        }
        Element wrap = docu.getElementById("wrap");
        if (wrap == null) {
            return null;
        }
        String data = wrap.data();
        if (data == null || !data.contains("?code=")) {
            return null;
        }

        int startIdx = data.indexOf("?code=") + 6;
        String val = data.substring(startIdx, startIdx + 6).trim();

        return val;
    }

    private long getCurrentPrice(String stockId, News news) {
        try {
            if (stockId == null) {
                log.info("stock id parse fail : " + news.getCompany());
                return 0L;
            }

            Document stockPage = Jsoup.connect(baseGetStockInfoUrl.replace("@A", stockId)).get();
            Elements comp = stockPage.getElementsByClass("no_today");
            Elements span = comp.select("span.blind");

            String priceStr = span.text().replaceAll(",", "");
            return Long.valueOf(priceStr.trim());
        } catch (Exception e) {
            log.info("unsupport encoding exception : " + news.getCompany());
        }
        return 0L;
    }

    public long getCurrentPrice(News news) {
        if (StringUtils.isEmpty(news.getCompany())) {
            return 0L;
        }

        if (news.getCompanyType().equals(CompanyType.ITOOZA)) {
            return this.getCurrentPrice(news.getCompany().split(" ")[1], news);
        }

        String stockId = this.getStockId(news.getCompany().split(" ")[0]);
        return this.getCurrentPrice(stockId, news);
    }
}

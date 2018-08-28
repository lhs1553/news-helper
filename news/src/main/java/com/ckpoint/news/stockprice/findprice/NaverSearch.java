package com.ckpoint.news.stockprice.findprice;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Component
public class NaverSearch {

    private final String baseGetStockIdUrl = "https://finance.naver.com/search/search.nhn?query=@A";
    private final String baseGetStockInfoUrl = "https://finance.naver.com/item/main.nhn?code=@A";

    private String getStockId(String companyName) {
        try {
            String encodeName = URLEncoder.encode(companyName, "euc-kr");
            Document docu = Jsoup.connect(baseGetStockIdUrl.replace("@A", encodeName)).get();
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

        } catch (IOException e) {
            log.info("unsupport encoding exception : " + companyName);
        }

        return null;
    }

    public long getCurrentPrice(String companyName) {
        try {
            String stockId = this.getStockId(companyName);
            if (stockId == null) {
                log.info("stock id parse fail : " + companyName);
                return 0L;
            }

            Document stockPage = Jsoup.connect(baseGetStockInfoUrl.replace("@A", stockId)).get();
            Elements comp = stockPage.getElementsByClass("no_today");
            Elements span = comp.select("span.blind");

            String priceStr = span.text().replaceAll(",", "");
            return Long.valueOf(priceStr.trim());
        } catch (Exception e) {
            log.info("unsupport encoding exception : " + companyName);
        }
        return 0L;

    }
}

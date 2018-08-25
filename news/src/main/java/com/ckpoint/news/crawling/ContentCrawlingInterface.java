package com.ckpoint.news.crawling;

import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;

import java.io.IOException;

public interface ContentCrawlingInterface {

    News setContent(News news) throws IOException;

    CompanyType getMyCompanyType();

}

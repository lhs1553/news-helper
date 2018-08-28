package com.ckpoint.news.news.repository;

import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByNewsIdAndCompanyType(String newsId, CompanyType companyType);

    List<News> findByStartPriceIsNotNullAndDayEndPriceIsNullAndCreatedAtAfter(Date standardDate);
}

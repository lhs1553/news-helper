package com.ckpoint.news.news.repository;

import com.ckpoint.news.news.CompanyType;
import com.ckpoint.news.news.domain.NewsFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsFilterRepository extends JpaRepository<NewsFilter, Long> {
    List<NewsFilter> findByCompanyType(CompanyType companyType);
}

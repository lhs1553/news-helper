package com.ckpoint.news.news.domain;

import com.ckpoint.news.common.BaseEntity;
import com.ckpoint.news.news.CompanyType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class NewsFilter extends BaseEntity {
    private String auth;
    private String keyword;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
}

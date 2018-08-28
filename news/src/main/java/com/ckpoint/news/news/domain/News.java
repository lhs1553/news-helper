package com.ckpoint.news.news.domain;

import com.ckpoint.news.common.BaseEntity;
import com.ckpoint.news.news.CompanyType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(indexes = {@Index(columnList = "newsId, companyType")})
public class News extends BaseEntity {

    private String auth;

    @Column(columnDefinition = "TEXT")
    private String newsId;

    private String company;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(columnDefinition = "TEXT")
    private String keyword;

    private Long startPrice;
    private Long dayEndPrice;

    public String getHeadInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(company);
        stringBuilder.append("\n\n");
        stringBuilder.append(auth);
        return stringBuilder.toString();

    }

}

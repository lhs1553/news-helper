package com.ckpoint.news.common;


import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}


package com.ckpoint.news.receiver.domain;

import com.ckpoint.news.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Receiver extends BaseEntity {

    private String name;
    private String sendPhone;
    private String phone;
}

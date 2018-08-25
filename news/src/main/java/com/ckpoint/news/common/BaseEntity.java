package com.ckpoint.news.common;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @PrePersist
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }

        if (this.updatedAt == null) {
            this.updatedAt = this.createdAt;
        }
    }

    /**
     * On pre update.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = new Date();
    }


}

package com.agranibank.NewsDirectory.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sayed Mahmud Raihan on 12/06/18.
 */

@Data
public class News {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal amount;
    private Agency agency;
    private Status status;
    private int quantity;
    private Date createdAt;
    private Date updatedAt;

    public enum Status{
        APPROVED , PENDING, ORDER, APPLICATION
    }
}

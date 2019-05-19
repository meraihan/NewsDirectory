package com.agranibank.NewsDirectory.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Sayed Mahmud Raihan on 12/06/18.
 */

@Data
public class Agency {
    private Integer id;
    private String agencyName;
    private String description;
    private AgencyType agencyType;
    private Date createAt;
    private Date updatedAt;

    public enum AgencyType {
        DAILY, FORTNIGHT, MONTHLY, OTHERS, WEEKLY
    }
}

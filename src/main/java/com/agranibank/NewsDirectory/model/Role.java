package com.agranibank.NewsDirectory.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Sayed Mahmud Raihan on 12/05/18.
 */

@Data
public class Role {
    private Integer id;
    private RoleName name;
    private String description;
    private Date createdAt;

    public enum RoleName {
        ADMIN, USER
    }
}

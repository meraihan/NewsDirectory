package com.agranibank.NewsDirectory.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Sayed Mahmud Raihan on 12/05/18.
 */

@Data
public class User {
    private Integer id;
    private String fullName;
    private String userName;
    private String password;
    private Role role;
    private Boolean isActive;
    private Gender gender;
    private String address;
    private String phone;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public enum  Gender {
       Male, Female
    }
}

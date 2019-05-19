package com.agranibank.NewsDirectory.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
        String password = "aaaaa@";
        System.out.println(bCryptEncoder.encode(password));
    }
}

package com.example.jsonex.service;

import com.example.jsonex.model.entity.User;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;
    User findRandomUser();
}

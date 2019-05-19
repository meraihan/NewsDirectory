package com.agranibank.NewsDirectory.service;

import com.agranibank.NewsDirectory.dao.UserDao;
import com.agranibank.NewsDirectory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<User> findAllUser() {
        List<User> userList = userDao.findAll();
        return userList;
    }

    public boolean addUser(User user) {
        if(userDao.add(user).getId() > 0){
            return true;
        }
        return false;
    }

    public User findById(Integer userId) {
        return userDao.findById(userId);
    }

    public boolean update(User user) {
        user.setUpdatedAt(new Date());
        return userDao.update(user);
    }
    public boolean delete(Integer userId) {
        return userDao.delete(userId);
    }
}



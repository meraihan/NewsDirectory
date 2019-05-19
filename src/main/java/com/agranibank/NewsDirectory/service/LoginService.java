package com.agranibank.NewsDirectory.service;


import com.agranibank.NewsDirectory.dao.RoleDao;
import com.agranibank.NewsDirectory.dao.UserDao;
import com.agranibank.NewsDirectory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Sayed Mahmud Raihan on 12/05/18.
 */

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        final User user = userDao.findUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User name: " + userName + " not found.");
        }
        final String roleName = roleDao.findRoleNameById(user.getRole().getId());
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }

}

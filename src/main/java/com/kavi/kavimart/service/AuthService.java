package com.kavi.kavimart.service;

import com.kavi.kavimart.dao.UserDao;
import com.kavi.kavimart.exception.*;
import com.kavi.kavimart.model.Role;
import com.kavi.kavimart.model.User;
import com.kavi.kavimart.util.PasswordUtil;
import com.kavi.kavimart.util.ValidationUtil;

/** Applies registration and login rules without knowing JDBC details. */
public class AuthService {
    private final UserDao userDao;
    /** Creates an authentication service. */
    public AuthService(UserDao userDao){this.userDao=userDao;}
    /** Registers a buyer or seller; admin registration is intentionally unavailable. */
    public User register(String name,String email,String password,Role role)throws AppException{
        name=ValidationUtil.required(name,"Name",120);email=ValidationUtil.email(email);ValidationUtil.password(password);
        if(role!=Role.BUYER&&role!=Role.SELLER)throw new ValidationException("Choose Buyer or Seller.");
        if(userDao.findByEmail(email).isPresent())throw new ValidationException("An account with that email already exists.");
        User user=new User();user.setName(name);user.setEmail(email);user.setPasswordHash(PasswordUtil.hash(password));user.setRole(role);user.setId(userDao.create(user));return user;
    }
    /** Authenticates a user with bcrypt verification. */
    public User authenticate(String email,String password)throws AppException{
        email=ValidationUtil.email(email);ValidationUtil.password(password);
        User user=userDao.findByEmail(email).orElseThrow(()->new AuthenticationException("Email or password is incorrect."));
        if(!PasswordUtil.matches(password,user.getPasswordHash()))throw new AuthenticationException("Email or password is incorrect.");
        return user;
    }
}
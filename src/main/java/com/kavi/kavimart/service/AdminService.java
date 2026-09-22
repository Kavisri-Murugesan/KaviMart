package com.kavi.kavimart.service;

import com.kavi.kavimart.dao.ProductDao;
import com.kavi.kavimart.dao.UserDao;
import com.kavi.kavimart.exception.AppException;
import com.kavi.kavimart.model.User;
import java.util.List;

/** Coordinates administrator-only moderation views. */
public class AdminService {
    private final UserDao userDao; private final ProductDao productDao; private final OrderService orderService;
    /** Creates an administrator service. */
    public AdminService(UserDao userDao,ProductDao productDao,OrderService orderService){this.userDao=userDao;this.productDao=productDao;this.orderService=orderService;}
    /** Lists all users. */
    public List<User> users()throws AppException{return userDao.findAll();}
    /** Lists all orders. */
    public java.util.List<com.kavi.kavimart.model.Order> orders()throws AppException{return orderService.allOrders();}
    /** Lists all current listings. */
    public java.util.List<com.kavi.kavimart.model.Product> products()throws AppException{return productDao.search(null,null);}
    /** Removes a moderated listing. */
    public void removeProduct(long productId)throws AppException{productDao.delete(productId);}
}
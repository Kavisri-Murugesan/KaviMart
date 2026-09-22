package com.kavi.kavimart.service;

import com.kavi.kavimart.dao.UserDao;
import com.kavi.kavimart.model.Role;
import com.kavi.kavimart.model.User;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Unit tests for authentication business rules using Mockito. */
class AuthServiceTest {
    @Test void registrationHashesPasswordAndCreatesSeller() throws Exception {
        UserDao dao=mock(UserDao.class);when(dao.findByEmail("new@example.com")).thenReturn(Optional.empty());when(dao.create(any(User.class))).thenReturn(44L);
        User result=new AuthService(dao).register("New Seller","new@example.com","securepass",Role.SELLER);
        assertEquals(44L,result.getId());assertEquals(Role.SELLER,result.getRole());assertNotEquals("securepass",result.getPasswordHash());verify(dao).create(any(User.class));
    }
}
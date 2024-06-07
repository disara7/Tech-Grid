package com.ecom.service;

import java.util.List;

import com.ecom.model.UserDtls;

public interface UserService {
	UserDtls saveUser(UserDtls user);
    List<UserDtls> getAllUsers();
    UserDtls getUserById(Integer id);
    void deleteUser(Integer id);

}

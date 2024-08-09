package com.ecom.service;

import java.util.List;
import java.util.Optional;

import com.ecom.model.UserDtls;

public interface UserService {
	UserDtls saveUser(UserDtls user);
    List<UserDtls> getAllUsers();
    
    void deleteUser(Integer id);
    Optional<UserDtls> getUserById(String string);
    Optional<UserDtls> getUserByUsername(String string);
    UserDtls getUserById(Integer id);
    UserDtls createUser(UserDtls newUser);
    Optional<UserDtls> getUserByEmail(String string);

}

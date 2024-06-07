package com.ecom.service.impl;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDtls saveUser(UserDtls user) {
        return UserRepository.save(user);
    }

    @Override
    public List<UserDtls> getAllUsers() {
        return UserRepository.findAll();
    }

    @Override
    public UserDtls getUserById(Integer id) {
        return UserRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Integer id) {
        UserRepository.deleteById(id);
    }
}

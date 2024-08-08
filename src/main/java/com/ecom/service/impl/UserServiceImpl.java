package com.ecom.service.impl;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<UserDtls> getUserById(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public Optional<UserDtls> getUserByUsername(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByUsername'");
    }

    @Override
    public UserDtls createUser(UserDtls newUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
}

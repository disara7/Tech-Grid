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
    private UserRepository userRepository;

    @Override
    public UserDtls saveUser(UserDtls user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDtls> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDtls getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDtls> getUserById(String id) {
        try {
            return userRepository.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            // Handle the case where the ID is not a valid integer
            return Optional.empty();
        }
    }

    

    @Override
    public UserDtls createUser(UserDtls newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public Optional<UserDtls> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

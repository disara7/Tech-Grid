package com.ecom.controller;

import com.ecom.model.UserDtls;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDtlsController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDtls> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDtls getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDtls createUser(@RequestBody UserDtls userDtls) {
        return userService.saveUser(userDtls);
    }

    @PutMapping("/{id}")
    public UserDtls updateUser(@PathVariable Integer id, @RequestBody UserDtls userDtls) {
        userDtls.setId(id);
        return userService.saveUser(userDtls);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}

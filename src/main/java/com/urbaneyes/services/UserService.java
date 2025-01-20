package com.urbaneyes.service;

import com.urbaneyes.model.User;
import com.urbaneyes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return "Login successful";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

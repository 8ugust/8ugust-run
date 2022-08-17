package com.august.run.Service;

import java.util.List;
import java.util.ArrayList;
import com.august.run.Model.User;
import org.springframework.stereotype.Service;
import com.august.run.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Find All
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        
        return users;
    }

    // Create User
    public User save(User user) {
        userRepository.save(user);
        
        return user;
    }
    
}

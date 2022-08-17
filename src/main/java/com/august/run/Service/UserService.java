package com.august.run.Service;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.august.run.Model.User;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import com.august.run.Request.UserRequest;
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
    public String signUp(UserRequest request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        userRepository.save(
            User.builder()
                .loginId(request.getLoginId())
                .loginPw(request.getLoginPw())
                .loginName(request.getLoginName())
                .loginGender(request.getLoginGender())
                .loginBirth(LocalDate.parse(request.getLoginBirth(), formatter))
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build()
        );
        
        return "Success";
    }
    
}

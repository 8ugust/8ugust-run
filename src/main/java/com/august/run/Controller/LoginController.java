package com.august.run.Controller;

import java.util.List;
import com.august.run.Model.User;
import lombok.RequiredArgsConstructor;
import com.august.run.Request.UserRequest;
import org.springframework.http.MediaType;
import com.august.run.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    // Find All User
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // Save User
    @PostMapping("/signUp")
    public ResponseEntity singUp(@RequestBody UserRequest request) {
        if (userService.signUp(request).equals("Success")) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    
    
}

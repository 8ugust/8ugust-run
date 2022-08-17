package com.august.run.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.august.run.Model.User;
import org.springframework.http.MediaType;
import com.august.run.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    @RequestMapping(value = "/saveUser", method = RequestMethod.GET)
    public ResponseEntity<User> save(HttpServletRequest req, User user) {
        
        return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
    }
    
}

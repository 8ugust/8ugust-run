package com.august.run.Controller;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.august.run.Model.User;
import lombok.RequiredArgsConstructor;
import com.august.run.Request.UserRequest;
import org.springframework.http.MediaType;
import com.august.run.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("TEST", "SUCCESS");
        return response;
    }    

    /**
     * Get User Data All
     * 
     * @return
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getUserAll() {
        List<User> users = userService.getUserAll();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }





    /**
     * Get User Data One
     * 
     * @param request
     * @return
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<List<User>> getUserOne(@PathVariable("user_id") String user_id) {
        List<User> user = userService.getUserOne(user_id);

        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }





    /**
     * Set New User Data
     * 
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<HttpStatus> save(@RequestBody UserRequest request) {
        if (userService.save(request).equals("Success")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }





    /**
     * Update User Data
     * 
     * @param request
     * @return
     */
    @PutMapping("{user_id}")
    public ResponseEntity<HttpStatus> update(@RequestBody UserRequest request, @PathVariable String user_id) {
        if (userService.update(request, user_id).equals("Success")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    /**
     * Delete User Data
     * 
     * @param user_id
     * @return
     */
    @DeleteMapping("{user_id}")
    public Map<String, Object> delete(@PathVariable String user_id) {
        Map<String, Object> response = new HashMap<>();

        String result = userService.delete(user_id);
        if (result == "Success") response.put("result", "Success");
        if (result == "Fail") {
            response.put("result", "Fail");
            response.put("reason", "일치하는 회원 정보가 없습니다.");
        }

        return response;
    }





    /**
     * User Login
     * 
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> user) {
        return userService.login(user.get("user_id"), user.get("user_pw"));
    }
    
    
}

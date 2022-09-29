package com.august.run.Controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.august.run.Request.TokenRequest;
import com.august.run.Request.UserRequest;
import com.august.run.Service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    
    /**
     * User Login
     * 
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenRequest> login(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }





    /**
     * User Signup
     * 
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody UserRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = userService.signup(request);
            if (result.equals("success")) {
                response.put("result", "success");
                response.put("reason", "success");

                return response;
            } else throw new Exception(result);
            
        } catch (Exception e) {
            response.put("result", "error");
            response.put("reason", e.toString());
            return response;
        }
    }
}

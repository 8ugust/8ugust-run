package com.august.run.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;

import com.august.run.Request.UserRequest;
import com.august.run.Service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LoginController {
    
    @Autowired
    UserService userService;

    /**
     * Login
     * 
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        System.out.println("Login");
        return "login"; 
    }


    /**
     * singup
     * 
     * @return
     */
    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        System.out.println("signUp GET");
        return "signUp";
    }


    /**
     * singup
     * 
     * @return
     */
    @PostMapping("/signUp")
    public Map<String, Object> signUp(@ModelAttribute("userRequest") UserRequest userRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Log
            String result = userService.save(userRequest);
            if (result.equals("Success")) {
                response.put("Result", "Success");
            } else {
                response.put("Result", "Error");
                response.put("Reason", result);
            }
            
            return response;
            
        } catch (Exception e) {
            response.put("Result", "Error");
            response.put("Reason", e.toString());
            return response;
        }
    }
}

package com.august.run.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String signUp(@ModelAttribute("userRequest") UserRequest userRequest) {
        try {
            System.out.println("signUp POST");
            if (userService.save(userRequest).equals("Success")) {
                System.out.println("OK");
            }
            
            return "redirect:/login";

        } catch (Exception e) {
            return e.toString();
        }
    }
}

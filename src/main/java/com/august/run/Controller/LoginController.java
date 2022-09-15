package com.august.run.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.august.run.Request.UserRequest;
import com.august.run.Service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LoginController {
    
    private final UserService userService;

    /**
     * Login
     * 
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        System.out.println("Login");
        return "Login.html";
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
            userService.save(userRequest);
            return "redirect:/login";

        } catch (Exception e) {
            return e.toString();
        }
    }
}

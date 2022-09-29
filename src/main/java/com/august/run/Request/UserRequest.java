package com.august.run.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class UserRequest {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private String birth; 

    /**
     * Authentication Account
     * 
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}

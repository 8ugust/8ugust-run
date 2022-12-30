package com.august.run.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.august.run.Model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserRequest {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private String birth; 
    private String instagram;

    /**
     * 
     * @return
     */
    public static UserRequest userInfo(User user) {
        return UserRequest.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }

    /**
     * Authentication Account
     * 
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}

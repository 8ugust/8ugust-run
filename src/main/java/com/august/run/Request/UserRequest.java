package com.august.run.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class UserRequest {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private String birth;
    
}

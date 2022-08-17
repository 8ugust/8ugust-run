package com.august.run.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class UserRequest {
    private String loginId;
    private String loginPw;
    private String loginName;
    private String loginGender;
    private String loginBirth;
}

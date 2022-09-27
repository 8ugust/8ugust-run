package com.august.run.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
    
}

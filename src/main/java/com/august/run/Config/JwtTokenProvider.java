package com.august.run.Config;


import java.util.Date;
import java.util.Base64;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import javax.annotation.PostConstruct;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${properties.jwt_secret_key}")
    private String jwt_secret_key;

    // Token Vaild Time 30 minutes
    private long tokenValidTime = 30 * 60 * 1000L;
    private final CustomUserDetailService customUserDetailService;

    @PostConstruct
    protected void init() {
        jwt_secret_key = Base64.getEncoder().encodeToString(jwt_secret_key.getBytes());
    }





    /**
     * Create Authenticated Token
     * 
     * @param user_id
     * @return
     */
    public String createToken(String user_id) {
        Claims claims = Jwts.claims().setSubject(user_id);
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, jwt_secret_key)
            .compact();
    }





    /**
     *  Lookup Authentication Data in Token
     * 
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        String primary_key = Jwts.parser().setSigningKey(jwt_secret_key).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(primary_key);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }





    /**
     * Get Token in HTTP Request
     * 
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }





    /**
     * Validate Token
     *  
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwt_secret_key).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}

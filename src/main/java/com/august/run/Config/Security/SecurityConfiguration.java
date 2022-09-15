package com.august.run.Config.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    
    private final UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/signUp").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .loginPage("/login")
                .and()
            .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL 요청 또한 로그아웃 처리.
                .deleteCookies("JSESSIONID")    // 쿠키 삭제
                .invalidateHttpSession(true)    // 세션 종료
                .clearAuthentication(true);       // 권한 제거

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}

package com.august.run.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDate;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    private String userId;

    @Column(nullable = false, length = 1000)
    private String userPw;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 1)
    private String gender;
    
    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

}

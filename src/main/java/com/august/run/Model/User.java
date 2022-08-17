package com.august.run.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    private String loginId;

    @Column(nullable = false, length = 1000)
    private String loginPw;

    @Column(nullable = false, length = 100)
    private String loginName;

    @Column(nullable = false, length = 1)
    private String loginGender;

    @Column(nullable = false)
    private LocalDate loginBirth;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate updatedAt;

    @Column(nullable = true)
    private LocalDate deletedAt;

}

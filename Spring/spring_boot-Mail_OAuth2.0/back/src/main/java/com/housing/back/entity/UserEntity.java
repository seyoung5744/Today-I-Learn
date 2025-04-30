package com.housing.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "user") // jpql 에서 사용될 명칭
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;

    @Builder
    public UserEntity(String userId, String password, String email, String type, String role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.type = type;
        this.role = role;
    }
}

package com.kms.seft203.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.seft203.auth.request.RegisterRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user",schema="public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="username", nullable = false)
    private String username;

    @Column(name="email",length = 50)
    private String email;

    @JsonIgnore
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="full_name")
    private String fullName;

    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public static User createUser(RegisterRequest registerRequest)
    {
        System.out.println("Register request "+ registerRequest );
        return new User(0L,registerRequest.getUsername(),registerRequest.getEmail(),registerRequest.getPassword(),registerRequest.getFullName());
    }
}

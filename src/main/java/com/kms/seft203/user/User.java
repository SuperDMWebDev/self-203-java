package com.kms.seft203.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.request.RegisterRequest;
import com.kms.seft203.task.Task;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="user", schema ="public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="verification_code")
    private String verificationcode;



    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<Task> tasks;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<Dashboard> dashboards;


    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String username, String email, String password, String fullName, Boolean enabled, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.enabled = enabled;
        this.verificationcode = verificationCode;
    }

    public static User createUser(RegisterRequest registerRequest)
    {
        System.out.println("Register request "+ registerRequest );
        return new User(registerRequest.getUsername(),registerRequest.getEmail(),registerRequest.getPassword(),registerRequest.getFullName());
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        var user = (User) o ;
        return this.username.equals(user.username) && this.email.equals(user.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, password);
    }
}

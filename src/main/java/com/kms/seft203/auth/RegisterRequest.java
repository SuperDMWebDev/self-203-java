package com.kms.seft203.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
}

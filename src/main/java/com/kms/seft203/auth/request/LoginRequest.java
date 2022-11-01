package com.kms.seft203.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

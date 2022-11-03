package com.kms.seft203.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    private String token;
    private Long userId;
}

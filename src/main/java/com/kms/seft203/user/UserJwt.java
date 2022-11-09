package com.kms.seft203.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user_jwt", schema ="public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserJwt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="is_valid")
    private boolean isValid;

    @Column(name="refresh_token")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
}

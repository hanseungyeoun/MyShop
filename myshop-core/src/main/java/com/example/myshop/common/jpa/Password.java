package com.example.myshop.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
    @Column(name = "password")
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean match(PasswordEncoder encoder, String password) {
        return encoder.matches(password, value);
    }
}

package com.example.bankingservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DeterministicPasswordEncoder();
    }

    public class DeterministicPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
                return bytesToHex(encodedHash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 algorithm not available", e);
            }
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            String hashedPassword = encode(rawPassword);
            return hashedPassword.equals(encodedPassword);
        }

        private String bytesToHex(byte[] hash) {
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
    }
}

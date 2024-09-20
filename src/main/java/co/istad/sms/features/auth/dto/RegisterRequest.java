package co.istad.sms.features.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "<email is required>")
        @Email
        String email,
        @NotBlank(message = "<username is required>")
        String username,
        @NotBlank(message = "<password is required>")
        String password,
        @NotBlank(message = "<confirm password is required>")
        String confirmPassword
) {
}

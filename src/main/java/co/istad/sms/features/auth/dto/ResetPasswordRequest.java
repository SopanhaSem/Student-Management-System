package co.istad.sms.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
public record ResetPasswordRequest(
        @NotBlank String email,
        @NotBlank String resetCode,
        @NotBlank String newPassword,
        @NotBlank String confirmPassword
) {
}

package co.istad.sms.features.auth;

import co.istad.sms.features.auth.dto.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/refresh")
    JwtResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }


    @PostMapping("/login")
    JwtResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


    @PostMapping("/verify")
    void verify(@Valid @RequestBody VerifyRequest verifyRequest) {
        authService.verify(verifyRequest);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void register(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException {
        authService.register(registerRequest);
    }
    @PostMapping("/request-password-reset")
    void requestPasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) throws MessagingException {
        authService.requestPasswordReset(passwordResetRequest);
    }

    @PostMapping("/reset-password")
    void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
    }
}

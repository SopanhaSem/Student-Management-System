package co.istad.sms.features.auth;

import co.istad.sms.domain.EmailVerification;
import co.istad.sms.domain.User;
import co.istad.sms.domain.UserProfile;
import co.istad.sms.features.auth.dto.*;
import co.istad.sms.features.user.UserRepository;
import co.istad.sms.mapper.UserMapper;
import co.istad.sms.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminMail;

    private final String TOKEN_TYPE = "Bearer";

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtEncoder;
    private  JwtEncoder jwtEncoderRefreshToken;

    @Autowired
    @Qualifier("jwtEncoderRefreshToken")
    public void setJwtEncoderRefreshToken(JwtEncoder jwtEncoderRefreshToken) {
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
    }
    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.token());

        auth = jwtAuthenticationProvider.authenticate(auth);
        Instant now = Instant.now();

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuedAt(now)
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        String accessToken = jwt.getTokenValue();
        String refreshToken = refreshTokenRequest.token();

        return JwtResponse.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuedAt(now)
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
//                .claim("scope", scope)
                .build();
        JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuedAt(now)
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Refresh Token")
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(jwtClaimsSetRefreshToken);
        Jwt jwtRefreshToken = jwtEncoderRefreshToken.encode(jwtEncoderParametersRefreshToken);

        String accessToken = jwt.getTokenValue();
        String refreshToken = jwtRefreshToken.getTokenValue();

        return JwtResponse.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void verify(VerifyRequest verifyRequest) {
        User user = userRepository
                .findByEmail(verifyRequest.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Email doesn't exist"
                ));

        EmailVerification emailVerification = emailVerificationRepository
                .findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Verification failed"
                ));

        if (!emailVerification.getVerificationCode().equals(verifyRequest.verificationCode())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verification code is invalid");
        }

        if (LocalTime.now().isAfter(emailVerification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verification code is expired");
        }
        userRepository.save(user);
    }

    @Override
    public void register(RegisterRequest registerRequest) throws MessagingException {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        User user = userMapper.fromRegisterRequest(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(registerRequest.username());
        userProfile.setPhoneNumber("");
        userProfile.setAddress("");
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        user = userRepository.save(user);
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        emailVerification.setUser(user);
        emailVerificationRepository.save(emailVerification);
        String myHtml = String.format("""
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #f0f0f0; border-radius: 10px;">
            <div style="text-align: center; padding-bottom: 20px;">
                <img src="https://gdm-catalog-fmapi-prod.imgix.net/ProductLogo/6565bd61-0fe6-459e-9ae4-69ead84c1fc4.png" alt="Student Management System" style="width: 150px;">
            </div>
            <h2 style="text-align: center; color: #333;">Welcome to Student Management System</h2>
            <p style="font-size: 16px; color: #555; line-height: 1.6;">
                Dear User,<br><br>
                Thank you for registering with us. To complete your registration, please verify your email address by using the code below.
            </p>
            <div style="text-align: center; padding: 20px; background-color: #f9f9f9; border-radius: 5px;">
                <h1 style="font-size: 28px; color: #2e7d32;">%s</h1>
            </div>
            <p style="font-size: 16px; color: #555; line-height: 1.6;">
                If you did not request this verification, please ignore this email.
            </p>
            <hr style="border: none; height: 1px; background-color: #ddd; margin: 20px 0;">
            <p style="font-size: 14px; color: #999; text-align: center;">
                © 2024 Student Management System. All rights reserved.
            </p>
        </div>
        """, emailVerification.getVerificationCode());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Verify your Email");
        helper.setTo(user.getEmail());
        helper.setFrom(adminMail);
        helper.setText(myHtml, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void requestPasswordReset(PasswordResetRequest passwordResetRequest) throws MessagingException {
        User user = userRepository.findByEmail(passwordResetRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));

        String verificationCode = RandomUtil.random6Digits();
        LocalTime expiryTime = LocalTime.now().plusMinutes(5);

        Optional<EmailVerification> existingVerificationOptional = emailVerificationRepository.findByUser(user);
        if (existingVerificationOptional.isPresent()) {
            EmailVerification existingVerification = existingVerificationOptional.get();
            existingVerification.setVerificationCode(verificationCode);
            existingVerification.setExpiryTime(expiryTime);
            emailVerificationRepository.save(existingVerification);
        } else {
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setVerificationCode(verificationCode);
            emailVerification.setExpiryTime(expiryTime);
            emailVerification.setUser(user);
            emailVerificationRepository.save(emailVerification);
        }
        String resetEmailHtml = String.format("""
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px;">
            <h2>Password Reset Request</h2>
            <p>Please use the following code to reset your password:</p>
            <h1>%s</h1>
            <p>If you did not request this reset, please ignore this email.</p>
        </div>
        """, verificationCode);

        // Send the email
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Password Reset Request");
        helper.setTo(user.getEmail());
        helper.setFrom(adminMail);
        helper.setText(resetEmailHtml, true);
        javaMailSender.send(mimeMessage);
    }


    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.newPassword().equals(resetPasswordRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        User user = userRepository.findByEmail(resetPasswordRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));

        EmailVerification emailVerification = emailVerificationRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Reset code not found"));

        if (!emailVerification.getVerificationCode().equals(resetPasswordRequest.resetCode()) ||
                LocalTime.now().isAfter(emailVerification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired reset code");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        userRepository.save(user);
        emailVerificationRepository.delete(emailVerification);
    }
}

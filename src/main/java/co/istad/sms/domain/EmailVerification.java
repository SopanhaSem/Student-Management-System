package co.istad.sms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "email_verifications")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "verification_id", updatable = false, nullable = false)
    private Integer verificationId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, length = 6)
    private String verificationCode;

    @Column(nullable = false)
    private LocalTime expiryTime;
}

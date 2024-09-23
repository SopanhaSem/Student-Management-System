package co.istad.sms.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id",  updatable = false, nullable = false)
    private Integer profileId;

    @Column(length = 100)
    private String fullName;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user;
}

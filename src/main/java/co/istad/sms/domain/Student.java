package co.istad.sms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String dob;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100)
    private String address;

    @ManyToOne
    private Department department;

}

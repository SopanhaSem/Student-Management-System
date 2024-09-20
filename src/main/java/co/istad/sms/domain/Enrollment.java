package co.istad.sms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentId;

    @ManyToOne
    @JoinColumn(name = "course_id") // Foreign key to the Course entity
    private Course course;

    private LocalDate enrollmentDate;

    private String grade;

    @ManyToOne
    private User user;
}

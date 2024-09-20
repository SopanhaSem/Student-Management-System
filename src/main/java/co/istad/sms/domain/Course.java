package co.istad.sms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id",  updatable = false, nullable = false)
    private Integer courseId;

    @Column(nullable = false, length = 100)
    private String courseName;

    @Column(nullable = false)
    private Integer credits;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @ManyToOne
    private Department departments;
}

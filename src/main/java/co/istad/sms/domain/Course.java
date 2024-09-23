package co.istad.sms.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id", updatable = false, nullable = false)
    private Integer courseId;

    @Column(nullable = false, length = 100,unique = true)
    private String courseName;

    @Column(nullable = false)
    private Integer credits;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Enrollment> enrollments;
}

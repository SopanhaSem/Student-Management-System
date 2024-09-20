package co.istad.sms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id", updatable = false, nullable = false)
    private Integer departmentId;

    @Column(length = 100, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "department")
    private List<Student> students;

    @OneToMany(mappedBy = "departments")
    private List<Course> courses;

}

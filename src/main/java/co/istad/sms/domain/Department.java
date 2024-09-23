package co.istad.sms.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(length = 100, nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Course> courses;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;
}


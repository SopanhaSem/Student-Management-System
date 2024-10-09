package co.istad.sms.domain;

import co.istad.sms.util.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}

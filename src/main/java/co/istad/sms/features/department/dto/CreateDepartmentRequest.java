package co.istad.sms.features.department.dto;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Instructor;
import co.istad.sms.domain.Student;

import java.util.List;

public record CreateDepartmentRequest(
        String departmentName,
        List<Course> courses,
        List<Instructor> instructors,
        List<Student> students
) {
}

package co.istad.sms.features.student;

import co.istad.sms.domain.Student;
import co.istad.sms.features.student.dto.CreateStudentRequest;

import java.util.List;

public interface StudentService {
    Student createStudent(CreateStudentRequest request);
    Student updateStudent(Integer studentId, CreateStudentRequest request);
    void deleteStudent(Integer studentId);
    Student findStudentById(Integer studentId);
    List<Student> getAllStudents();
}
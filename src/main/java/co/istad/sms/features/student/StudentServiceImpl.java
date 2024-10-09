package co.istad.sms.features.student;

import co.istad.sms.domain.*;
import co.istad.sms.features.attendance.AttendanceRepository;
import co.istad.sms.features.department.DepartmentRepository;
import co.istad.sms.features.enrollment.EnrollmentRepository;
import co.istad.sms.features.grade.GradeRepository;
import co.istad.sms.features.student.dto.CreateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;

    @Override
    public Student createStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setAddress(request.address());
        student.setDob(request.dob());
        student.setEmail(request.email());

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        student.setDepartment(department);

        List<Attendance> attendances = attendanceRepository.findAllById(request.attendanceIds());
        student.setAttendances(attendances);

        List<Grade> grades = gradeRepository.findAllById(request.gradeIds());
        student.setGrades(grades);

        List<Enrollment> enrollments = enrollmentRepository.findAllById(request.enrollmentIds());
        student.setEnrollments(enrollments);

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Integer studentId, CreateStudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        student.setName(request.name());
        student.setAddress(request.address());
        student.setDob(request.dob());
        student.setEmail(request.email());

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        student.setDepartment(department);

        List<Attendance> attendances = attendanceRepository.findAllById(request.attendanceIds());
        student.setAttendances(attendances);

        List<Grade> grades = gradeRepository.findAllById(request.gradeIds());
        student.setGrades(grades);

        List<Enrollment> enrollments = enrollmentRepository.findAllById(request.enrollmentIds());
        student.setEnrollments(enrollments);

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        studentRepository.delete(student);
    }

    @Override
    public Student findStudentById(Integer studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}

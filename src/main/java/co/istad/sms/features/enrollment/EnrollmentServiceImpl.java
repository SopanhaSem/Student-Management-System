package co.istad.sms.features.enrollment;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Enrollment;
import co.istad.sms.domain.User;
import co.istad.sms.features.course.CourseRepository;
import co.istad.sms.features.enrollment.dto.EnrollmentRequest;
import co.istad.sms.features.enrollment.dto.EnrollmentResponse;
import co.istad.sms.features.user.UserRepository;
import co.istad.sms.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService{
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentMapper enrollmentMapper;
    @Override
    public EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest) {
        User user = userRepository.findById(enrollmentRequest.userId())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"User not found"
                ));
        Course course = courseRepository.findById(enrollmentRequest.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setGrade(enrollmentRequest.grade());
        enrollmentRepository.save(enrollment);

        return EnrollmentResponse.builder()
                .userId(enrollment.getUser().getUserId())
                .courseId(enrollment.getCourse().getCourseId())
                .grade(enrollment.getGrade())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }


    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
         return  enrollments.stream()
                 .map(enrollment -> EnrollmentResponse
                         .builder()
                         .userId(enrollment.getUser().getUserId())
                         .courseId(enrollment.getCourse().getCourseId())
                         .grade(enrollment.getGrade())
                         .enrollmentDate(enrollment.getEnrollmentDate())
                         .build())
                 .toList();
    }

    @Override
    public EnrollmentResponse getEnrollmentById(Integer enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        return EnrollmentResponse.builder()
                .userId(enrollment.getUser().getUserId())
                .courseId(enrollment.getCourse().getCourseId())
                .grade(enrollment.getGrade())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }

    @Override
    public void deleteEnrollment(Integer enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }
}

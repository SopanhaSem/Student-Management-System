package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Department;
import co.istad.sms.domain.Enrollment;
import co.istad.sms.domain.User;
import co.istad.sms.features.course.dto.CourseResponse;
import co.istad.sms.features.course.dto.CreateCourseRequest;
import co.istad.sms.features.department.DepartmentRepository;
import co.istad.sms.features.enrollment.dto.EnrollmentResponse;
import co.istad.sms.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    @Override
    public Course createCourse(CreateCourseRequest request) {
        if (courseRepository.existsByCourseName(request.courseName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course already existed");
        }
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Department not found")
                );
        Course course = new Course();
        course.setCourseName(request.courseName());
        course.setCredits(request.credits());
        course.setDepartment(department);

        List<Enrollment> enrollments = request.enrollment().stream()
                .map(enrollmentRequest -> {
                    User user = userRepository.findById(enrollmentRequest.userId())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "User not found")
                            );

                    Enrollment enrollment = new Enrollment();
                    enrollment.setUser(user);
                    enrollment.setCourse(course);
                    enrollment.setFullName(enrollmentRequest.fullName());
                    enrollment.setGender(enrollmentRequest.gender());
                    enrollment.setDob(enrollmentRequest.dob());
                    enrollment.setPlaceOfBirth(enrollmentRequest.placeOfBirth());
                    enrollment.setCurrentAddress(enrollmentRequest.currentAddress());
                    enrollment.setEmail(enrollmentRequest.email());
                    enrollment.setPhoneNumber(enrollmentRequest.phoneNumber());
                    enrollment.setEnrollmentDate(LocalDate.now());
                    enrollment.setGrade(enrollmentRequest.grade());

                    return enrollment;
                })
                .collect(Collectors.toList());
        course.setEnrollments(enrollments);
        return courseRepository.save(course);
    }



    @Override
    public Course updateCourse(Integer courseId, CreateCourseRequest request) {
        return courseRepository.findById(courseId).map(course -> {
            course.setCourseName(request.courseName());
            course.setCredits(request.credits());
            if (request.enrollment() != null) {
                List<Enrollment> updatedEnrollments = request.enrollment().stream()
                        .map(enrollmentRequest -> {
                            User user = userRepository.findById(enrollmentRequest.userId())
                                    .orElseThrow(() -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND, "User not found")
                                    );

                            Enrollment enrollment = new Enrollment();
                            enrollment.setUser(user);
                            enrollment.setCourse(course);
                            enrollment.setFullName(enrollmentRequest.fullName());
                            enrollment.setGender(enrollmentRequest.gender());
                            enrollment.setDob(enrollmentRequest.dob());
                            enrollment.setPlaceOfBirth(enrollmentRequest.placeOfBirth());
                            enrollment.setCurrentAddress(enrollmentRequest.currentAddress());
                            enrollment.setEmail(enrollmentRequest.email());
                            enrollment.setPhoneNumber(enrollmentRequest.phoneNumber());
                            enrollment.setEnrollmentDate(LocalDate.now());
                            enrollment.setGrade(enrollmentRequest.grade());

                            return enrollment;
                        })
                        .collect(Collectors.toList());
                course.setEnrollments(updatedEnrollments);
            }
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not found"));
    }

    @Override
    public ResponseEntity<CourseResponse> getCourseById(Integer courseId) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course Not found"
                        ));
        List<EnrollmentResponse> enrollmentResponses = course.getEnrollments().stream()
                .map(enrollment -> new EnrollmentResponse(
                        enrollment.getUser().getUserId(),
                        enrollment.getCourse().getCourseId(),
                        enrollment.getFullName(),
                        enrollment.getGender(),
                        enrollment.getDob(),
                        enrollment.getPlaceOfBirth(),
                        enrollment.getCurrentAddress(),
                        enrollment.getEmail(),
                        enrollment.getPhoneNumber(),
                        enrollment.getGrade()
                ))
                .collect(Collectors.toList());
        CourseResponse response = new CourseResponse(
                course.getCourseId(),
                course.getCourseName(),
                course.getCredits(),
                enrollmentResponses
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(course -> {
                    List<EnrollmentResponse> enrollmentResponses = course.getEnrollments().stream()
                            .map(enrollment -> new EnrollmentResponse(
                                    enrollment.getUser().getUserId(),
                                    enrollment.getCourse().getCourseId(),
                                    enrollment.getFullName(),
                                    enrollment.getGender(),
                                    enrollment.getDob(),
                                    enrollment.getPlaceOfBirth(),
                                    enrollment.getCurrentAddress(),
                                    enrollment.getEmail(),
                                    enrollment.getPhoneNumber(),
                                    enrollment.getGrade()
                            ))
                            .collect(Collectors.toList());

                    return new CourseResponse(
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getCredits(),
                            enrollmentResponses
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourseById(Integer courseId) {
        courseRepository.deleteById(courseId);
    }
}

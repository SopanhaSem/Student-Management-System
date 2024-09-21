package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Department;
import co.istad.sms.domain.Enrollment;
import co.istad.sms.features.course.dto.CreateCourseRequest;
import co.istad.sms.features.department.DepartmentRepository;
import co.istad.sms.features.enrollment.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    @Override
    public Course createCourse(CreateCourseRequest request) {

        Department department = departmentRepository.findById(request.departmentId()).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department not found"
                )
        );

        Course course = new Course();
        course.setCourseName(request.courseName());
        course.setCredits(request.credits());
        course.setDepartments(department);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Integer courseId, CreateCourseRequest request) {
        return courseRepository.findById(courseId).map(course -> {
            course.setCourseName(request.courseName());
            course.setCredits(request.credits());
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    @Override
    public Optional<Course> getCourseById(Integer courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteCourseById(Integer courseId) {
        courseRepository.deleteById(courseId);
    }
}

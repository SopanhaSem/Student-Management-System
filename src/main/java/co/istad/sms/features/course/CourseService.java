package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import co.istad.sms.features.course.dto.CourseResponse;
import co.istad.sms.features.course.dto.CreateCourseRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course createCourse(CreateCourseRequest request);
    Course updateCourse(Integer courseId, CreateCourseRequest request);
    ResponseEntity<CourseResponse> getCourseById(Integer courseId);
    List<CourseResponse> getAllCourses();
    void deleteCourseById(Integer courseId);
}

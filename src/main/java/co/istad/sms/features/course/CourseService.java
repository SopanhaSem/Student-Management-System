package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import co.istad.sms.features.course.dto.CreateCourseRequest;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course createCourse(CreateCourseRequest request);
    Course updateCourse(Integer courseId, CreateCourseRequest request);
    Optional<Course> getCourseById(Integer courseId);
    List<Course> getAllCourses();
    void deleteCourseById(Integer courseId);
}

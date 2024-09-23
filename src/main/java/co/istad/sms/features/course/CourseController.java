package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import co.istad.sms.features.course.dto.CourseResponse;
import co.istad.sms.features.course.dto.CreateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public void createCourse(@RequestBody CreateCourseRequest request) {
       courseService.createCourse(request);
    }


    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer courseId, @RequestBody CreateCourseRequest request) {
        Course updatedCourse = courseService.updateCourse(courseId, request);
        return ResponseEntity.ok(updatedCourse);
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Integer courseId) {
        return courseService.getCourseById(courseId);
    }


    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }


    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }
}

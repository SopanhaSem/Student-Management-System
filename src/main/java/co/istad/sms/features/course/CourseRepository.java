package co.istad.sms.features.course;

import co.istad.sms.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseId(Integer courseId);
    Boolean existsByCourseName(String courseName);
}

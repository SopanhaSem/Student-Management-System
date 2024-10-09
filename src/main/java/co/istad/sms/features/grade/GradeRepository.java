package co.istad.sms.features.grade;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Grade;
import co.istad.sms.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Integer> {
    List<Grade> findByStudent(Student student);
    List<Grade> findByCourse(Course course);
}

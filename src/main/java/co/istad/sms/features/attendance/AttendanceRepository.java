package co.istad.sms.features.attendance;

import co.istad.sms.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    List<Attendance> findStudentById(Integer studentId);
    List<Attendance> findCourseById(Integer courseId);
}

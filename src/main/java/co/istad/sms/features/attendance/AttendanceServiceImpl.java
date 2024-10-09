package co.istad.sms.features.attendance;

import co.istad.sms.domain.Attendance;
import co.istad.sms.domain.Course;
import co.istad.sms.domain.Student;
import co.istad.sms.features.attendance.dto.CreateAttendanceRequest;
import co.istad.sms.features.attendance.dto.ResponseAttendance;
import co.istad.sms.features.course.CourseRepository;
import co.istad.sms.features.student.StudentRepository;
import co.istad.sms.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public ResponseAttendance createAttendance(CreateAttendanceRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toResponseAttendance(savedAttendance);
    }

    @Override
    public ResponseAttendance updateAttendance(Integer attendanceId, CreateAttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance not found"));

        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setStatus(request.status());

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toResponseAttendance(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Integer attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance not found"));

        attendanceRepository.delete(attendance);
    }

    @Override
    public ResponseAttendance findAttendanceById(Integer attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance not found"));

        return attendanceMapper.toResponseAttendance(attendance);
    }

    @Override
    public List<ResponseAttendance> getAllAttendancesForStudent(Integer studentId) {
        List<Attendance> attendances = attendanceRepository.findStudentById(studentId);
        return attendanceMapper.toResponseAttendanceList(attendances);
    }

    @Override
    public List<ResponseAttendance> getAllAttendancesForCourse(Integer courseId) {
        List<Attendance> attendances = attendanceRepository.findCourseById(courseId);
        return attendanceMapper.toResponseAttendanceList(attendances);
    }

    @Override
    public List<ResponseAttendance> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendanceMapper.toResponseAttendanceList(attendances);
    }
}

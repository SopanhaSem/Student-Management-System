package co.istad.sms.features.attendance;

import co.istad.sms.features.attendance.dto.CreateAttendanceRequest;
import co.istad.sms.features.attendance.dto.ResponseAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseAttendance createAttendance(@RequestBody CreateAttendanceRequest request) {
        return attendanceService.createAttendance(request);
    }

    @PutMapping("/{attendanceId}")
    public ResponseAttendance updateAttendance(@PathVariable Integer attendanceId, @RequestBody CreateAttendanceRequest request) {
        return attendanceService.updateAttendance(attendanceId, request);
    }

    @DeleteMapping("/{attendanceId}")
    public void deleteAttendance(@PathVariable Integer attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }

    @GetMapping("/{attendanceId}")
    public ResponseAttendance findAttendanceById(@PathVariable Integer attendanceId) {
        return attendanceService.findAttendanceById(attendanceId);
    }

    @GetMapping("/student/{studentId}")
    public List<ResponseAttendance> getAllAttendancesForStudent(@PathVariable Integer studentId) {
        return attendanceService.getAllAttendancesForStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<ResponseAttendance> getAllAttendancesForCourse(@PathVariable Integer courseId) {
        return attendanceService.getAllAttendancesForCourse(courseId);
    }

    @GetMapping
    public List<ResponseAttendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }
}

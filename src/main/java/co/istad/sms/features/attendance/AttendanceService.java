package co.istad.sms.features.attendance;

import co.istad.sms.features.attendance.dto.CreateAttendanceRequest;
import co.istad.sms.features.attendance.dto.ResponseAttendance;

import java.util.List;

public interface AttendanceService {
    ResponseAttendance createAttendance(CreateAttendanceRequest request);
    ResponseAttendance updateAttendance(Integer attendanceId, CreateAttendanceRequest request);
    void deleteAttendance(Integer attendanceId);
    ResponseAttendance findAttendanceById(Integer attendanceId);
    List<ResponseAttendance> getAllAttendancesForStudent(Integer studentId);
    List<ResponseAttendance> getAllAttendancesForCourse(Integer courseId);
    List<ResponseAttendance> getAllAttendances();
}

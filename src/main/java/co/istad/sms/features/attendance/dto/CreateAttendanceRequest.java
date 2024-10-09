package co.istad.sms.features.attendance.dto;

import co.istad.sms.util.AttendanceStatus;

import java.time.LocalDate;

public record CreateAttendanceRequest(
        Integer studentId,
        Integer courseId,
        LocalDate attendanceDate,
        AttendanceStatus status
) {
}

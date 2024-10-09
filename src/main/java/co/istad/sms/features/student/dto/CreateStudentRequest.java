package co.istad.sms.features.student.dto;

import java.util.List;

public record CreateStudentRequest(
        String name,
        String dob,
        String email,
        String address,
        Integer departmentId,
        List<Integer> attendanceIds,
        List<Integer> gradeIds,
        List<Integer> enrollmentIds
) {
}

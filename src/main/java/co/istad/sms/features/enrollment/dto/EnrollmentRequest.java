package co.istad.sms.features.enrollment.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EnrollmentRequest(
        Integer userId,
        Integer courseId,
        Integer studentId,
        String fullName,
        String gender,
        LocalDate dob,
        String placeOfBirth,
        String currentAddress,
        String email,
        String phoneNumber,
        String courseName,
        String grade
) {
}

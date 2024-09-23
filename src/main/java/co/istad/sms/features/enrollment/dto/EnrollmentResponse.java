package co.istad.sms.features.enrollment.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnrollmentResponse(
        Integer userId,
        Integer courseId,
        String fullName,
        String gender,
        LocalDate dob,
        String placeOfBirth,
        String currentAddress,
        String email,
        String phoneNumber,
        String grade
) {
}

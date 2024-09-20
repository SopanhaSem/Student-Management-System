package co.istad.sms.features.enrollment.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnrollmentResponse(
        Integer userId,
        Integer courseId,
        String grade,
        LocalDate enrollmentDate
) {
}

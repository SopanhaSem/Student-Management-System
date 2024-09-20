package co.istad.sms.features.enrollment.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EnrollmentRequest(
        Integer userId,
        Integer courseId,
        String courseName,
        String grade
) {
}

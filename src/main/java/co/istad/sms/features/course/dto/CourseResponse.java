package co.istad.sms.features.course.dto;

import co.istad.sms.features.enrollment.dto.EnrollmentResponse;

import java.util.List;

public record CourseResponse(
        Integer courseId,
        String courseName,
        Integer credits,
        List<EnrollmentResponse> enrollments
) {}


package co.istad.sms.features.course.dto;

import co.istad.sms.domain.Enrollment;
import co.istad.sms.features.enrollment.dto.EnrollmentRequest;

import java.util.List;

public record CreateCourseRequest(
        String courseName,
        Integer credits,
        Integer departmentId,
        List<EnrollmentRequest> enrollment
) {
}

package co.istad.sms.features.course.dto;

public record CreateCourseRequest(
        String courseName,
        Integer credits,
        Integer departmentId
) {
}

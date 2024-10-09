package co.istad.sms.features.instructor.dto;

import lombok.Builder;

@Builder
public record CreateInstructorRequest(
        String name,
        String email,
        String dob,
        String address,
        Integer departmentId
) {
}

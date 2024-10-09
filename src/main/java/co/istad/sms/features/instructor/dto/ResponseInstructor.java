package co.istad.sms.features.instructor.dto;

import lombok.Builder;

@Builder
public record ResponseInstructor(
        Integer instructorId,
        String name,
        String email,
        String dob,
        String address,
        String departmentName
) {
}

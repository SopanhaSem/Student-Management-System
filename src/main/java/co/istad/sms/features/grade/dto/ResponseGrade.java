package co.istad.sms.features.grade.dto;

import co.istad.sms.util.AssessmentType;

import java.time.LocalDate;

public record ResponseGrade(
        Integer id,
        Integer studentId,
        Integer courseId,
        Double grade,
        LocalDate gradedOn,
        AssessmentType assessmentType
) {
}
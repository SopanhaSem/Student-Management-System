package co.istad.sms.features.grade.dto;

import co.istad.sms.util.AssessmentType;

import java.time.LocalDate;

public record CreateGradeRequest(
        Integer studentId,
        Integer courseId,
        Double grade,
        LocalDate gradedOn,
        AssessmentType assessmentType
) {
}
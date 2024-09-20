package co.istad.sms.mapper;

import co.istad.sms.domain.Enrollment;
import co.istad.sms.features.enrollment.dto.EnrollmentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentRequest toEnrollmentRequest(Enrollment enrollment);
    EnrollmentRequest toEnrollmentResponse(Enrollment enrollment);
}

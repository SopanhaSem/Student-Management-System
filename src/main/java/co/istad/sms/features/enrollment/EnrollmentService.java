package co.istad.sms.features.enrollment;

import co.istad.sms.features.enrollment.dto.EnrollmentRequest;
import co.istad.sms.features.enrollment.dto.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest);
    List<EnrollmentResponse> getAllEnrollments();
    EnrollmentResponse getEnrollmentById(Integer enrollmentId);
    void deleteEnrollment(Integer enrollmentId);
}

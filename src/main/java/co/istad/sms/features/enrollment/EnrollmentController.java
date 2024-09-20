package co.istad.sms.features.enrollment;

import co.istad.sms.features.enrollment.dto.EnrollmentRequest;
import co.istad.sms.features.enrollment.dto.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<EnrollmentResponse> enrollStudent(
            @RequestBody EnrollmentRequest enrollmentRequest) {
        EnrollmentResponse enrollmentResponses =  enrollmentService.enrollStudent(enrollmentRequest);
        return ResponseEntity.ok(enrollmentResponses);
    }

    @GetMapping
    ResponseEntity<List<EnrollmentResponse>> getAllEnrollment(){
        List<EnrollmentResponse> enrollmentResponses = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollmentResponses);
    }

    @GetMapping("/{id}")
    ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Integer id){
            EnrollmentResponse enrollmentResponse = enrollmentService.getEnrollmentById(id);
            return ResponseEntity.ok(enrollmentResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEnrollmentById(@PathVariable Integer id){
        enrollmentService.deleteEnrollment(id);
    }
}

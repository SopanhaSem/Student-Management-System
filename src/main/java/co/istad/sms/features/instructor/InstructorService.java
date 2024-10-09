package co.istad.sms.features.instructor;

import co.istad.sms.features.instructor.dto.CreateInstructorRequest;
import co.istad.sms.features.instructor.dto.ResponseInstructor;

import java.util.List;

public interface InstructorService {
    ResponseInstructor createInstructor(CreateInstructorRequest request);
    ResponseInstructor updateInstructor(Integer instructorId, CreateInstructorRequest request);
    void deleteInstructor(Integer instructorId);
    public ResponseInstructor findInstructorById(Integer instructorId);
    List<ResponseInstructor> getAllInstructors();
}

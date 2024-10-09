package co.istad.sms.features.instructor;

import co.istad.sms.domain.Department;
import co.istad.sms.domain.Instructor;
import co.istad.sms.features.department.DepartmentRepository;
import co.istad.sms.features.instructor.dto.CreateInstructorRequest;
import co.istad.sms.features.instructor.dto.ResponseInstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseInstructor createInstructor(CreateInstructorRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        Instructor instructor = new Instructor();
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        instructor.setDob(request.dob());
        instructor.setAddress(request.address());
        instructor.setDepartment(department);

        instructor = instructorRepository.save(instructor);

        return ResponseInstructor.builder()
                .instructorId(instructor.getInstructorId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .dob(instructor.getDob())
                .address(instructor.getAddress())
                .departmentName(instructor.getDepartment().getDepartmentName())
                .build();
    }

    @Override
    public ResponseInstructor updateInstructor(Integer instructorId, CreateInstructorRequest request) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found"));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        instructor.setName(request.name());
        instructor.setEmail(request.email());
        instructor.setDob(request.dob());
        instructor.setAddress(request.address());
        instructor.setDepartment(department);

        Instructor updatedInstructor = instructorRepository.save(instructor);

        return ResponseInstructor.builder()
                .instructorId(updatedInstructor.getInstructorId())
                .name(updatedInstructor.getName())
                .email(updatedInstructor.getEmail())
                .dob(updatedInstructor.getDob())
                .address(updatedInstructor.getAddress())
                .departmentName(updatedInstructor.getDepartment().getDepartmentName())
                .build();
    }

    @Override
    public void deleteInstructor(Integer instructorId) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found");
        }
        instructorRepository.deleteById(instructorId);
    }

    @Override
    public ResponseInstructor findInstructorById(Integer instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found"));

        return ResponseInstructor.builder()
                .instructorId(instructor.getInstructorId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .dob(instructor.getDob())
                .address(instructor.getAddress())
                .departmentName(instructor.getDepartment().getDepartmentName())
                .build();
    }

    @Override
    public List<ResponseInstructor> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructor -> ResponseInstructor.builder()
                        .instructorId(instructor.getInstructorId())
                        .name(instructor.getName())
                        .email(instructor.getEmail())
                        .dob(instructor.getDob())
                        .address(instructor.getAddress())
                        .departmentName(instructor.getDepartment().getDepartmentName())
                        .build())
                .collect(Collectors.toList());
    }
}

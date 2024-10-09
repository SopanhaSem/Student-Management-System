package co.istad.sms.features.instructor;

import co.istad.sms.features.instructor.dto.CreateInstructorRequest;
import co.istad.sms.features.instructor.dto.ResponseInstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseInstructor createInstructor(@RequestBody CreateInstructorRequest request) {
        return instructorService.createInstructor(request);
    }

    @PutMapping("/{instructorId}")
    public ResponseInstructor updateInstructor(@PathVariable Integer instructorId,
                                               @RequestBody CreateInstructorRequest request) {
        return instructorService.updateInstructor(instructorId, request);
    }

    @GetMapping("/{instructorId}")
    public ResponseInstructor getInstructorById(@PathVariable Integer instructorId) {
        return instructorService.findInstructorById(instructorId);
    }

    @GetMapping
    public List<ResponseInstructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @DeleteMapping("/{instructorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstructor(@PathVariable Integer instructorId) {
        instructorService.deleteInstructor(instructorId);
    }
}

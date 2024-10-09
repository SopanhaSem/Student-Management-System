package co.istad.sms.features.grade;

import co.istad.sms.domain.Grade;
import co.istad.sms.features.grade.dto.CreateGradeRequest;
import co.istad.sms.features.grade.dto.ResponseGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grades")
public class GradeController {
    private final GradeService gradeService;

    @PostMapping
    public ResponseGrade createGrade(@RequestBody CreateGradeRequest request) {
        return gradeService.createGrade(request);
    }

    @PutMapping("/{gradeId}")
    public ResponseGrade updateGrade(@PathVariable Integer gradeId, @RequestBody CreateGradeRequest request) {
        return gradeService.updateGrade(gradeId, request);
    }

    @DeleteMapping("/{gradeId}")
    public void deleteGrade(@PathVariable Integer gradeId) {
        gradeService.deleteGrade(gradeId);
    }

    @GetMapping("/{gradeId}")
    public ResponseGrade findGradeById(@PathVariable Integer gradeId) {
        return gradeService.findGradeById(gradeId);
    }

    @GetMapping("/student/{studentId}")
    public List<ResponseGrade> getAllGradesForStudent(@PathVariable Integer studentId) {
        return gradeService.getAllGradesForStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<ResponseGrade> getAllGradesForCourse(@PathVariable Integer courseId) {
        return gradeService.getAllGradesForCourse(courseId);
    }
    @GetMapping
    public List<ResponseGrade> getAllGrades() {
        return gradeService.getAllGrades();
    }
}

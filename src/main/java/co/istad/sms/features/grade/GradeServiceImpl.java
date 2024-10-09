package co.istad.sms.features.grade;

import co.istad.sms.domain.Grade;
import co.istad.sms.domain.Student;
import co.istad.sms.domain.Course;
import co.istad.sms.features.grade.dto.CreateGradeRequest;
import co.istad.sms.features.grade.dto.ResponseGrade;
import co.istad.sms.features.student.StudentRepository;
import co.istad.sms.features.course.CourseRepository;
import co.istad.sms.features.grade.GradeRepository;
import co.istad.sms.mapper.GradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeMapper gradeMapper;
    @Override
    public ResponseGrade createGrade(CreateGradeRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGrade(request.grade());
        grade.setGradedOn(request.gradedOn());
        grade.setAssessmentType(request.assessmentType());

        Grade savedGrade = gradeRepository.save(grade);
        return gradeMapper.toResponseGrade(savedGrade);
    }
    @Override
    public List<ResponseGrade> getAllGrades() {
        List<Grade> grades = gradeRepository.findAll();
        return grades.stream()
                .map(gradeMapper::toResponseGrade)
                .collect(Collectors.toList());
    }
    @Override
    public ResponseGrade updateGrade(Integer gradeId, CreateGradeRequest request) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));

        grade.setGrade(request.grade());
        grade.setGradedOn(request.gradedOn());
        grade.setAssessmentType(request.assessmentType());

        Grade updatedGrade = gradeRepository.save(grade);
        return gradeMapper.toResponseGrade(updatedGrade);
    }

    @Override
    public ResponseGrade findGradeById(Integer gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));
        return gradeMapper.toResponseGrade(grade);
    }

    @Override
    public List<ResponseGrade> getAllGradesForStudent(Integer studentId) {
        List<Grade> grades = gradeRepository.findByStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")));
        return grades.stream().map(gradeMapper::toResponseGrade).collect(Collectors.toList());
    }

    @Override
    public List<ResponseGrade> getAllGradesForCourse(Integer courseId) {
        List<Grade> grades = gradeRepository.findByCourse(courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found")));
        return grades.stream().map(gradeMapper::toResponseGrade).collect(Collectors.toList());
    }
    @Override
    public void deleteGrade(Integer gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));
        gradeRepository.delete(grade);
    }

}

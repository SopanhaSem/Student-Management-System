package co.istad.sms.features.grade;

import co.istad.sms.domain.Grade;
import co.istad.sms.features.grade.dto.CreateGradeRequest;
import co.istad.sms.features.grade.dto.ResponseGrade;

import java.util.List;

public interface GradeService {
    ResponseGrade createGrade(CreateGradeRequest request);
    ResponseGrade updateGrade(Integer gradeId, CreateGradeRequest request);
    void deleteGrade(Integer gradeId);
    ResponseGrade findGradeById(Integer gradeId);
    List<ResponseGrade> getAllGradesForCourse(Integer courseId);
    List<ResponseGrade> getAllGradesForStudent(Integer studentId);
    List<ResponseGrade> getAllGrades();
}

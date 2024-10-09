package co.istad.sms.mapper;

import co.istad.sms.domain.Grade;
import co.istad.sms.features.grade.dto.ResponseGrade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    @Mapping(source = "student.studentId", target = "studentId")
    @Mapping(source = "course.courseId", target = "courseId")
    ResponseGrade toResponseGrade(Grade grade);
}

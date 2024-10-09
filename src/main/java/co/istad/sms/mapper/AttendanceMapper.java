package co.istad.sms.mapper;

import co.istad.sms.domain.Attendance;
import co.istad.sms.features.attendance.dto.ResponseAttendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "student.studentId", target = "studentId")
    @Mapping(source = "course.courseId", target = "courseId")
    ResponseAttendance toResponseAttendance(Attendance attendance);

    List<ResponseAttendance> toResponseAttendanceList(List<Attendance> attendances);
}

package co.istad.sms.features.department;

import co.istad.sms.domain.Course;
import co.istad.sms.domain.Department;
import co.istad.sms.domain.Instructor;
import co.istad.sms.domain.Student;
import co.istad.sms.features.course.CourseRepository;
import co.istad.sms.features.department.dto.CreateDepartmentRequest;
import co.istad.sms.features.department.dto.ResponseDepartment;
import co.istad.sms.features.instructor.InstructorRepository;
import co.istad.sms.features.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    @Override
    public ResponseDepartment createDepartment(CreateDepartmentRequest createDepartmentRequest) {
        if (departmentRepository.existsByDepartmentName(createDepartmentRequest.departmentName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Department already exists"
            );
        }

        Department department = new Department();
        department.setDepartmentName(createDepartmentRequest.departmentName());

        List<Course> courses = courseRepository.findAllById(createDepartmentRequest.courseIds());
        List<Instructor> instructors = instructorRepository.findAllById(createDepartmentRequest.instructorIds());
        List<Student> students = studentRepository.findAllById(createDepartmentRequest.studentIds());

        department.setCourses(courses);
        department.setInstructors(instructors);
        department.setStudents(students);

        department = departmentRepository.save(department);

        List<Integer> courseIds = courses.stream().map(Course::getCourseId).toList();
        List<Integer> instructorIds = instructors.stream().map(Instructor::getInstructorId).toList();
        List<Integer> studentIds = students.stream().map(Student::getStudentId).toList();

        return new ResponseDepartment(
                department.getDepartmentName(),
                courseIds,
                instructorIds,
                studentIds
        );
    }

    @Override
    public ResponseDepartment updateDepartment(Integer departmentId, CreateDepartmentRequest createDepartmentRequest) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Department not found with id: " + departmentId)
                );

        if (departmentRepository.existsByDepartmentName(createDepartmentRequest.departmentName()) &&
                !existingDepartment.getDepartmentName().equals(createDepartmentRequest.departmentName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Department name already exists"
            );
        }

        existingDepartment.setDepartmentName(createDepartmentRequest.departmentName());

        List<Course> courses = courseRepository.findAllById(createDepartmentRequest.courseIds());
        List<Instructor> instructors = instructorRepository.findAllById(createDepartmentRequest.instructorIds());
        List<Student> students = studentRepository.findAllById(createDepartmentRequest.studentIds());

        existingDepartment.setCourses(courses);
        existingDepartment.setInstructors(instructors);
        existingDepartment.setStudents(students);

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        List<Integer> courseIds = courses.stream().map(Course::getCourseId).toList();
        List<Integer> instructorIds = instructors.stream().map(Instructor::getInstructorId).toList();
        List<Integer> studentIds = students.stream().map(Student::getStudentId).toList();

        return new ResponseDepartment(
                updatedDepartment.getDepartmentName(),
                courseIds,
                instructorIds,
                studentIds
        );
    }

    @Override
    public List<ResponseDepartment> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department -> {
                    List<Integer> courseIds = department.getCourses().stream().map(Course::getCourseId).toList();
                    List<Integer> instructorIds = department.getInstructors().stream().map(Instructor::getInstructorId).toList();
                    List<Integer> studentIds = department.getStudents().stream().map(Student::getStudentId).toList();

                    return new ResponseDepartment(
                            department.getDepartmentName(),
                            courseIds,
                            instructorIds,
                            studentIds
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDepartment getDepartmentById(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Department not found with id: " + departmentId)
                );

        List<Integer> courseIds = department.getCourses().stream().map(Course::getCourseId).toList();
        List<Integer> instructorIds = department.getInstructors().stream().map(Instructor::getInstructorId).toList();
        List<Integer> studentIds = department.getStudents().stream().map(Student::getStudentId).toList();

        return new ResponseDepartment(
                department.getDepartmentName(),
                courseIds,
                instructorIds,
                studentIds
        );
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with id: " + departmentId);
        }

        departmentRepository.deleteById(departmentId);
    }
}


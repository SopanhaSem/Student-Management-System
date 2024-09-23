package co.istad.sms.features.department;

import co.istad.sms.domain.Department;
import co.istad.sms.features.department.dto.CreateDepartmentRequest;
import co.istad.sms.features.department.dto.ResponseDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseDepartment createDepartment(CreateDepartmentRequest createDepartmentRequest) {
        if(departmentRepository.existsByDepartmentName(createDepartmentRequest.departmentName())){
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department is existed"
            );
        }
        Department department = new Department();
        department.setDepartmentName(createDepartmentRequest.departmentName());
        department = departmentRepository.save(department);

        return new ResponseDepartment(
                department.getDepartmentName(),
                createDepartmentRequest.courses(),
                createDepartmentRequest.instructors(),
                createDepartmentRequest.students()
        );
    }

    @Override
    public List<ResponseDepartment> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();

        // Map to ResponseDepartment
        return departments.stream()
                .map(department -> new ResponseDepartment(
                        department.getDepartmentName(),
                        department.getCourses(),
                        department.getInstructors(),
                        department.getStudents()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDepartment getDepartmentById(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Department not found with id: " + departmentId)
                );

        return new ResponseDepartment(
                department.getDepartmentName(),
                department.getCourses(),
                department.getInstructors(),
                department.getStudents()
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

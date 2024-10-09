package co.istad.sms.features.department;

import co.istad.sms.features.department.dto.CreateDepartmentRequest;
import co.istad.sms.features.department.dto.ResponseDepartment;
import co.istad.sms.features.enrollment.dto.EnrollmentRequest;
import co.istad.sms.features.enrollment.dto.EnrollmentResponse;

import java.util.List;

public interface DepartmentService {
    ResponseDepartment createDepartment(CreateDepartmentRequest createDepartmentRequest);
    ResponseDepartment updateDepartment(Integer departmentId,CreateDepartmentRequest createDepartmentRequest);
    List<ResponseDepartment> getAllDepartment();
    ResponseDepartment getDepartmentById(Integer departmentId);
    void deleteDepartment(Integer departmentId);
}

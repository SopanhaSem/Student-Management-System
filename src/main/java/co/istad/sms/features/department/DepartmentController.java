package co.istad.sms.features.department;

import co.istad.sms.features.department.dto.CreateDepartmentRequest;
import co.istad.sms.features.department.dto.ResponseDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ResponseDepartment> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        ResponseDepartment response = departmentService.createDepartment(createDepartmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ResponseDepartment> updateDepartment(
            @PathVariable Integer departmentId,
            @RequestBody CreateDepartmentRequest createDepartmentRequest) {
        ResponseDepartment updatedDepartment = departmentService.updateDepartment(departmentId, createDepartmentRequest);
        return ResponseEntity.ok(updatedDepartment);
    }
    @GetMapping
    public ResponseEntity<List<ResponseDepartment>> getAllDepartments() {
        List<ResponseDepartment> departments = departmentService.getAllDepartment();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ResponseDepartment> getDepartmentById(@PathVariable Integer departmentId) {
        ResponseDepartment response = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }
}

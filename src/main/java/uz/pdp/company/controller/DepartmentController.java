package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Department;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.DepartmentDto;
import uz.pdp.company.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> post(@RequestBody DepartmentDto departmentDto) {
        return departmentService.post(departmentDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        return departmentService.update(id, departmentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return departmentService.delete(id);
    }
}

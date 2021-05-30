package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.entity.Department;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.payload.DepartmentDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;
import uz.pdp.company.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("department by this id not exist", false));
    }

    public ResponseEntity<ApiResponse> post(DepartmentDto departmentDto) {
        String name = departmentDto.getName();
        Long companyId = departmentDto.getCompanyId();

        if (!name.isEmpty()) {
            if (companyId != null) {
                Optional<Company> company = companyRepository.findById(companyId);
                if (company.isPresent()) {
                    Department department = new Department();
                    department.setName(departmentDto.getName());
                    department.setCompany(company.get());
                    departmentRepository.save(department);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("department saqlandi", true));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday company mavjud emas", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("company id bush bumasin", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("department name bush bumasin", false));
    }

    public ResponseEntity<ApiResponse> update(Long id, DepartmentDto departmentDto) {
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isPresent()) {
            String name = departmentDto.getName();
            Long companyId = departmentDto.getCompanyId();

            if (!name.isEmpty()) {
                if (companyId != null) {
                    Optional<Company> company = companyRepository.findById(companyId);
                    if (company.isPresent()) {
                        Department department = new Department();
                        department.setName(departmentDto.getName());
                        department.setCompany(company.get());
                        departmentRepository.save(department);
                        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("department saqlandi", true));
                    }
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday company mavjud emas", false));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("company id bush bumasin", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("department name bush bumasin", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday department mavjud emas", false));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isPresent()) {
            departmentRepository.deleteById(id);
            Long companyId = byId.get().getCompany().getId();
            companyRepository.deleteById(companyId);
            return ResponseEntity.ok(new ApiResponse("deleted", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday idli department mavjud emas", false));
    }
}

package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Company;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> post(@RequestBody CompanyDto companyDto) {
        return companyService.post(companyDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        return companyService.update(id, companyDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return companyService.delete(id);
    }

}

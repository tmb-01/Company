package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.company.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

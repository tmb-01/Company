package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}

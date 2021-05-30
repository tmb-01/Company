package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.company.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}

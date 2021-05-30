package uz.pdp.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.company.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

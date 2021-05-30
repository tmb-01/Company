package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.entity.Department;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.payload.WorkerDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;
import uz.pdp.company.repository.DepartmentRepository;
import uz.pdp.company.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Worker> byId = workerRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("worker by this id not exist", false));
    }

    public ResponseEntity<ApiResponse> post(WorkerDto workerDto) {
        int homeNumber = workerDto.getHomeNumber();
        String street = workerDto.getStreet();
        String workerName = workerDto.getName();
        String phoneNumber = workerDto.getPhoneNumber();
        Long departmentId = workerDto.getDepartmentId();

        if (!street.isEmpty() && homeNumber != 0) {
            if (!workerName.isEmpty()) {
                if (!phoneNumber.isEmpty()) {
                    boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(phoneNumber);
                    if (!existsByPhoneNumber) {
                        Optional<Department> departmentById = departmentRepository.findById(departmentId);
                        if (departmentById.isPresent()) {
                            Address address = new Address();
                            address.setHomeNumber(workerDto.getHomeNumber());
                            address.setStreet(workerDto.getStreet());
                            Address savedAddress = addressRepository.save(address);

                            Worker worker = new Worker();
                            worker.setAddress(savedAddress);
                            worker.setName(workerName);
                            worker.setPhoneNumber(phoneNumber);
                            worker.setDepartment(departmentById.get());
                            workerRepository.save(worker);
                            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("worker saqlandi", true));
                        }
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("department mavjud emas", false));
                    }
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("bunday tel raqam mavjud", false));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("tel raqam bush bumasin", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("worker name bush bumasin", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("address bush bumasin", false));
    }

    public ResponseEntity<ApiResponse> update(Long id, WorkerDto workerDto) {
        int homeNumber = workerDto.getHomeNumber();
        String street = workerDto.getStreet();
        String workerName = workerDto.getName();
        String phoneNumber = workerDto.getPhoneNumber();
        Long departmentId = workerDto.getDepartmentId();

        Optional<Worker> workerById = workerRepository.findById(id);

        if (workerById.isPresent()) {

            if (!street.isEmpty() && homeNumber != 0) {
                if (!workerName.isEmpty()) {
                    if (!phoneNumber.isEmpty()) {
                        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);
                        if (!existsByPhoneNumberAndIdNot) {
                            Optional<Department> departmentById = departmentRepository.findById(departmentId);
                            if (departmentById.isPresent()) {
                                Address address = new Address();
                                address.setHomeNumber(workerDto.getHomeNumber());
                                address.setStreet(workerDto.getStreet());
                                Address savedAddress = addressRepository.save(address);

                                Worker worker = new Worker();
                                worker.setAddress(savedAddress);
                                worker.setName(workerName);
                                worker.setPhoneNumber(phoneNumber);
                                worker.setDepartment(departmentById.get());
                                workerRepository.save(worker);
                                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("worker saqlandi", true));
                            }
                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("department mavjud emas", false));
                        }
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("bunday tel raqam mavjud", false));
                    }
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("tel raqam bush bumasin", false));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("worker name bush bumasin", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("address bush bumasin", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("worker mavjud emas", false));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Worker> workerById = workerRepository.findById(id);
        if (workerById.isPresent()) {
            workerRepository.deleteById(id);
            Long addressId = workerById.get().getAddress().getId();
            addressRepository.deleteById(addressId);
            return ResponseEntity.ok(new ApiResponse("deleted", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday idli worker mavjud emas", false));
    }
}

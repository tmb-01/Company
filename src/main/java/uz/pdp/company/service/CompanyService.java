package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("company by this id not exist", false));
    }

    public ResponseEntity<ApiResponse> post(CompanyDto companyDto) {
        int homeNumber = companyDto.getHomeNumber();
        String street = companyDto.getStreet();
        String corpName = companyDto.getCorpName();
        String directorName = companyDto.getDirectorName();

        if (!street.isEmpty() && homeNumber != 0) {
            if (!corpName.isEmpty()) {
                if (!directorName.isEmpty()) {
                    Address address = new Address();
                    address.setHomeNumber(companyDto.getHomeNumber());
                    address.setStreet(companyDto.getStreet());
                    Address savedAddress = addressRepository.save(address);
                    System.out.println(savedAddress);
                    Company company = new Company();
                    company.setCorpName(corpName);
                    company.setDirectorName(directorName);
                    company.setAddress(savedAddress);
                    companyRepository.save(company);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("company saqlandi", true));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("director name bush bumasin", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("corporation name bush bumasin", false));

        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("address bush bumasin", false));
    }

    public ResponseEntity<ApiResponse> update(Long id, CompanyDto companyDto) {
        int homeNumber = companyDto.getHomeNumber();
        String street = companyDto.getStreet();
        String corpName = companyDto.getCorpName();
        String directorName = companyDto.getDirectorName();
        if (!street.isEmpty() && homeNumber != 0) {
            if (!corpName.isEmpty()) {
                if (!directorName.isEmpty()) {
                    Optional<Company> byId = companyRepository.findById(id);
                    if (byId.isPresent()) {
                        Company company = byId.get();
                        Address address = company.getAddress();
                        address.setHomeNumber(companyDto.getHomeNumber());
                        address.setStreet(companyDto.getStreet());
                        Address savedAddress = addressRepository.save(address);

                        company.setCorpName(corpName);
                        company.setDirectorName(directorName);
                        company.setAddress(savedAddress);
                        companyRepository.save(company);
                        return ResponseEntity.ok(new ApiResponse("company uzgartirildi", true));
                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("bunaqa idli company bavjud emas", false));
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("director name bush bumasin", false));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("corporation name bush bumasin", false));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("address bush bumasin", false));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            companyRepository.deleteById(id);
            Long addressId = byId.get().getAddress().getId();
            addressRepository.deleteById(addressId);
            return ResponseEntity.ok(new ApiResponse("deleted", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("bunday idli company mavjud emas", false));
    }
}

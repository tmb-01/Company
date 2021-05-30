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
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAll() {
        return addressRepository.findAll();
    }
}

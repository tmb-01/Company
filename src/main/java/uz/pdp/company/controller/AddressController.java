package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.payload.ApiResponse;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.service.AddressService;
import uz.pdp.company.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public List<Address> getAll() {
        return addressService.getAll();
    }

}

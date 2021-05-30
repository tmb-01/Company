package uz.pdp.company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Department;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkerDto {
    private Long id;
    private String name;
    private String phoneNumber;

    private String street;
    private int homeNumber;

    private Long departmentId;
}

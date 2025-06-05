package ge.nikazhordania.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private Long departmentId;
    private Long managerId;
    private String phone;
    private String address;
    private Double salary;
    private String email;
    private LocalDate birthDate;
}

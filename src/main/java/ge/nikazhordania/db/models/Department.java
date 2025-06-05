package ge.nikazhordania.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private String name;
    private Long companyId;

    public Department(String name, Long companyId) {
        this.name = name;
        this.companyId = companyId;
    }
}

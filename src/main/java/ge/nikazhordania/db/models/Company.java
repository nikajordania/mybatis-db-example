package ge.nikazhordania.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;
    private String name;

    public Company(String name) {
        this.name = name;
    }
}

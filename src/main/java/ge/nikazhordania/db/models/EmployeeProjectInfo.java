package ge.nikazhordania.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProjectInfo {
    private Long employeeId;
    private String employeeName;
    private String departmentName;
    private String companyName;
    private Long projectId;
    private String projectName;
}

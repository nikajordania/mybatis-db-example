package ge.nikazhordania.db.mapper;

import ge.nikazhordania.db.models.Employee;
import ge.nikazhordania.db.models.EmployeeProjectInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee findById(Long id);

    @Insert("""
        INSERT INTO employee (name, department_id, manager_id, phone, address, salary, email, birth_date)
        VALUES (#{name}, #{departmentId}, #{managerId}, #{phone}, #{address}, #{salary}, #{email}, #{birthDate})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Employee employee);

    @Select("SELECT * FROM employee WHERE department_id = #{departmentId}")
    List<Employee> findByDepartmentId(Long departmentId);

    @Select("SELECT * FROM employee WHERE manager_id = #{managerId}")
    List<Employee> findSubordinates(Long managerId);

    @Select("""
                SELECT
                    e.id AS employee_id,
                    e.name AS employee_name,
                    d.name AS department_name,
                    c.name AS company_name,
                    p.id AS project_id,
                    p.name AS project_name
                FROM employee e
                JOIN department d ON e.department_id = d.id
                JOIN company c ON d.company_id = c.id
                LEFT JOIN employee_project ep ON e.id = ep.employee_id
                LEFT JOIN project p ON ep.project_id = p.id
                WHERE e.id = #{employeeId}
            """)
    @Results(id = "employeeProjectInfoMap", value = {
            @Result(column = "employee_id", property = "employeeId"),
            @Result(column = "employee_name", property = "employeeName"),
            @Result(column = "department_name", property = "departmentName"),
            @Result(column = "company_name", property = "companyName"),
            @Result(column = "project_id", property = "projectId"),
            @Result(column = "project_name", property = "projectName"),
    })
    List<EmployeeProjectInfo> getEmployeeProjectInfo(@Param("employeeId") Long employeeId);
}

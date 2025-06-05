package ge.nikazhordania.db.mapper;

import ge.nikazhordania.db.models.Employee;
import ge.nikazhordania.db.models.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeProjectMapper {

    @Insert("""
        INSERT INTO employee_project (employee_id, project_id)
        VALUES (#{employeeId}, #{projectId})
    """)
    void assignEmployeeToProject(@Param("employeeId") Long employeeId, @Param("projectId") Long projectId);

    @Select("""
        SELECT p.* FROM project p
        JOIN employee_project ep ON ep.project_id = p.id
        WHERE ep.employee_id = #{employeeId}
    """)
    List<Project> findProjectsByEmployeeId(Long employeeId);

    @Select("""
        SELECT e.* FROM employee e
        JOIN employee_project ep ON ep.employee_id = e.id
        WHERE ep.project_id = #{projectId}
    """)
    List<Employee> findEmployeesByProjectId(Long projectId);
}

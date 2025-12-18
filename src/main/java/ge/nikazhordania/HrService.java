package ge.nikazhordania;

import com.github.javafaker.Faker;
import ge.nikazhordania.config.BaseConfig;
import ge.nikazhordania.db.mapper.*;
import ge.nikazhordania.db.models.*;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.util.List;

import static ge.nikazhordania.config.BaseConfig.sqlSessionFactory;

public class HrService {

    public static void main(String[] args) {

        Faker faker = new Faker();

        try (SqlSession session = sqlSessionFactory.openSession()) {

            CompanyMapper companyMapper = session.getMapper(CompanyMapper.class);

            // 1. Company
            Company company = new Company(faker.company().name());
            companyMapper.insert(company);

            // 2. Department
            DepartmentMapper departmentMapper = session.getMapper(DepartmentMapper.class);

            Department department = new Department(faker.company().industry(), company.getId());
            departmentMapper.insert(department);

            // 3. Manager
            EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);

            Employee manager = new Employee();
            manager.setName(faker.name().fullName());
            manager.setDepartmentId(department.getId());
            manager.setPhone(faker.phoneNumber().cellPhone());
            manager.setAddress(faker.address().fullAddress());
            manager.setSalary(200.0);
            manager.setEmail(faker.internet().emailAddress());
            manager.setBirthDate(LocalDate.of(1990, 1, 1));

            employeeMapper.insert(manager);

            // 4. Employee
            Employee employee = new Employee();
            employee.setName(faker.name().fullName());
            employee.setDepartmentId(department.getId());
            employee.setManagerId(manager.getId());
            employee.setPhone(faker.phoneNumber().cellPhone());
            employee.setAddress(faker.address().fullAddress());
            employee.setSalary(100.0);
            employee.setEmail(faker.internet().emailAddress());
            employee.setBirthDate(LocalDate.of(1990, 1, 1));

            employeeMapper.insert(employee);

            // 5. Project
            ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);

            Project project = new Project(faker.app().name());
            projectMapper.insert(project);

            // 6. Assign
            EmployeeProjectMapper employeeProjectMapper = session.getMapper(EmployeeProjectMapper.class);
            employeeProjectMapper.assignEmployeeToProject(employee.getId(), project.getId());

            // 7. Projects
            List<Project> projects = employeeProjectMapper.findProjectsByEmployeeId(employee.getId());

            projects.forEach(p -> System.out.println("Project: " + p.getName()));

            // 8. Info
            List<EmployeeProjectInfo> infoList = employeeMapper.getEmployeeProjectInfo(employee.getId());

            EmployeeProjectInfo info = infoList.getFirst();
            System.out.println("Employee ID: " + info.getEmployeeId());
            System.out.println("Employee Name: " + info.getEmployeeName());
            System.out.println("Project ID: " + info.getProjectId());
            System.out.println("Project Name: " + info.getProjectName());
            System.out.println("Department Name: " + info.getDepartmentName());
            System.out.println("Company Name: " + info.getCompanyName());
        }
    }
}

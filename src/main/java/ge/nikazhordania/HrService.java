package ge.nikazhordania;

import com.github.javafaker.Faker;
import ge.nikazhordania.config.BaseConfig;
import ge.nikazhordania.db.mapper.*;
import ge.nikazhordania.db.models.*;

import java.time.LocalDate;
import java.util.List;

public class HrService {

    public static void main(String[] args) {

        Faker faker = new Faker(); // to generate random data

        // Initialize mappers to work with the database tables
        CompanyMapper companyMapper = BaseConfig.companyMapper();
        DepartmentMapper departmentMapper = BaseConfig.departmentMapper();
        EmployeeMapper employeeMapper = BaseConfig.employeeMapper();
        ProjectMapper projectMapper = BaseConfig.projectMapper();
        EmployeeProjectMapper employeeProjectMapper = BaseConfig.employeeProjectMapper();

        // 1. Insert company object
        Company company = new Company(faker.company().name());

        // Note: company.getId() will be null before insertion, it will be populated after insertion
        companyMapper.insert(company);

        // 2. Insert department
        Department department = new Department(faker.company().industry(), company.getId());
        departmentMapper.insert(department);

        // 3. Insert manager
        Employee manager = new Employee();
        manager.setName(faker.name().fullName());
        manager.setDepartmentId(department.getId());
        manager.setManagerId(null);
        manager.setPhone(faker.phoneNumber().cellPhone());
        manager.setAddress(faker.address().fullAddress());
        manager.setSalary(200.0);
        manager.setEmail(faker.internet().emailAddress());
        manager.setBirthDate(LocalDate.parse("1990-01-01"));

        employeeMapper.insert(manager);

        // 4. Insert employee under the manager
        Employee employee = new Employee();
        employee.setName(faker.name().fullName());
        employee.setDepartmentId(department.getId());
        employee.setManagerId(manager.getId());
        employee.setPhone(faker.phoneNumber().cellPhone());
        employee.setAddress(faker.address().fullAddress());
        employee.setSalary(100.0);
        employee.setEmail(faker.internet().emailAddress());
        employee.setBirthDate(LocalDate.parse("1990-01-01"));

        employeeMapper.insert(employee);

        // 5. Insert project
        Project project = new Project(faker.app().name());
        projectMapper.insert(project);

        // 6. Assign employee to project
        employeeProjectMapper.assignEmployeeToProject(employee.getId(), project.getId());

        // 7. Fetch and print assigned projects
        List<Project> projects = employeeProjectMapper.findProjectsByEmployeeId(employee.getId());
        projects.forEach(p -> System.out.println("Project: " + p.getName()));

        // 8. Fetch and print employee project info
        List<EmployeeProjectInfo> infoList = employeeMapper.getEmployeeProjectInfo(employee.getId());

        EmployeeProjectInfo firstUserInfo = infoList.getFirst();
        System.out.println("Employee ID: " + firstUserInfo.getEmployeeId());
        System.out.println("Employee Name: " + firstUserInfo.getEmployeeName());
        System.out.println("Project ID: " + firstUserInfo.getProjectId());
        System.out.println("Project Name: " + firstUserInfo.getProjectName());
        System.out.println("Department Name: " + firstUserInfo.getDepartmentName());
        System.out.println("Company Name: " + firstUserInfo.getCompanyName());
    }
}

package ge.nikazhordania.db.mapper;

import ge.nikazhordania.db.models.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("SELECT * FROM department WHERE id = #{id}")
    Department findById(Long id);

    @Insert("INSERT INTO department (name, company_id) VALUES (#{name}, #{companyId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Department department);

    @Select("SELECT * FROM department WHERE company_id = #{companyId}")
    List<Department> findByCompanyId(Long companyId);
}

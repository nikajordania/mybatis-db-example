package ge.nikazhordania.db.mapper;

import ge.nikazhordania.db.models.Company;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyMapper {
    
    @Select("SELECT * FROM company WHERE id = #{id}")
    Company findById(Long id);

    @Insert("INSERT INTO company (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Company company);
    
    @Select("SELECT * FROM company")
    List<Company> findAll();
}

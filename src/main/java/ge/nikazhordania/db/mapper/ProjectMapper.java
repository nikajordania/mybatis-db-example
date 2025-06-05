package ge.nikazhordania.db.mapper;

import ge.nikazhordania.db.models.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper {

    @Select("SELECT * FROM project WHERE id = #{id}")
    Project findById(Long id);

    @Insert("INSERT INTO project (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Project project);

    @Select("SELECT * FROM project")
    List<Project> findAll();
}

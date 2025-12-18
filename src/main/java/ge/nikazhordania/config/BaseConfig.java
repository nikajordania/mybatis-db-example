package ge.nikazhordania.config;

import ge.nikazhordania.db.mapper.*;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class BaseConfig {

    public static final SqlSessionFactory sqlSessionFactory;

    static {
        String jdbcUrl = "jdbc:h2:./companyDB;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement stmt = conn.createStatement()) {

            String createTables = Files.readString(Path.of("src/main/resources/scripts/create_tables.sql"));
            stmt.execute(createTables);

            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("org.h2.Driver");
            dataSource.setUrl(jdbcUrl);

            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("development", transactionFactory, dataSource);

            Configuration configuration = new Configuration(environment);
            configuration.addMapper(CompanyMapper.class);
            configuration.addMapper(DepartmentMapper.class);
            configuration.addMapper(EmployeeMapper.class);
            configuration.addMapper(ProjectMapper.class);
            configuration.addMapper(EmployeeProjectMapper.class);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}

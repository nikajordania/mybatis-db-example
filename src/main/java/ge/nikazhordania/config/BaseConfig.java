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

public class BaseConfig {

    private static SqlSessionFactory sqlSessionFactory;

    //    init SqlSessionFactory once per jvm execution
    static {

        // jdbc URL embedded mode for H2 database (no server required, just a file as database)
        String jdbcUrl = "jdbc:h2:./companyDB;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement stmt = conn.createStatement()) {
            String createTables = Files.readString(Path.of("src/main/resources/scripts/create_tables.sql"));

            // Create tables if they do not exist
            stmt.execute(createTables);

            System.out.println("Tables created successfully.");


            // Create a pooled data source
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("org.h2.Driver");
            dataSource.setUrl(jdbcUrl);

            // Initialize MyBatis environment
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("development", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);

            // Register mappers (mappers are interfaces that MyBatis will use to map SQL queries to Java methods)
            configuration.addMapper(CompanyMapper.class);
            configuration.addMapper(DepartmentMapper.class);
            configuration.addMapper(EmployeeMapper.class);
            configuration.addMapper(ProjectMapper.class);
            configuration.addMapper(EmployeeProjectMapper.class);

            // Build the SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methods to get mappers for each table
    public static CompanyMapper companyMapper() {
        return sqlSessionFactory.openSession(true).getMapper(CompanyMapper.class);
    }

    public static DepartmentMapper departmentMapper() {
        return sqlSessionFactory.openSession(true).getMapper(DepartmentMapper.class);
    }

    public static EmployeeMapper employeeMapper() {
        return sqlSessionFactory.openSession(true).getMapper(EmployeeMapper.class);
    }

    public static ProjectMapper projectMapper() {
        return sqlSessionFactory.openSession(true).getMapper(ProjectMapper.class);
    }

    public static EmployeeProjectMapper employeeProjectMapper() {
        return sqlSessionFactory.openSession(true).getMapper(EmployeeProjectMapper.class);
    }
}

package ir.mci;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceConnectionService {

    private DataSourceConnectionService() {
    }

    private static DataSource dataSource;

    static {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setSchema("alv");
        ds.setUsername("postgres");
        ds.setPassword("admin");
        dataSource = ds;
    }

    public static JdbcClient getJdbcClient() {
        return JdbcClient.create(dataSource);
    }

}

package ir.mci;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;

public class DataSourceConnectionService {

    private DataSourceConnectionService() {
    }

    private static final DataSource DATA_SOURCE;
    private static final JdbcClient JDBC_CLIENT;
    private static final BasicDataSource BASIC_DATA_SOURCE = new BasicDataSource();

    static {
        BASIC_DATA_SOURCE.setDriverClassName("org.postgresql.Driver");
        BASIC_DATA_SOURCE.setUrl("jdbc:postgresql://localhost:5432/postgres");
        BASIC_DATA_SOURCE.setConnectionInitSqls(List.of("SET SCHEMA 'alv'")); // schema name for sql queries
        BASIC_DATA_SOURCE.setUsername("postgres");
        BASIC_DATA_SOURCE.setPassword("admin");
        BASIC_DATA_SOURCE.setMinIdle(5);
        BASIC_DATA_SOURCE.setMaxIdle(10);
        BASIC_DATA_SOURCE.setMaxOpenPreparedStatements(100);
        DATA_SOURCE = BASIC_DATA_SOURCE;
        JDBC_CLIENT = JdbcClient.create(DATA_SOURCE);
    }

    public static JdbcClient getJdbcClient() {
        return JDBC_CLIENT;
    }

}

package ir.mci;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RunDBQuery implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();

        JdbcClient jdbcClient = DataSourceConnectionService.getJdbcClient();
        List<Map<String, Object>> res = jdbcClient.sql("select * from test").query().listOfRows();

        Utils.generateResponse(exchange, Utils.getGson().toJson(res));
    }

}

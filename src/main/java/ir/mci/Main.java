package ir.mci;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/ali", new Ali());
        server.createContext("/saeid", new Saeid());

        server.createContext("/start", new StartJob(executor));
        server.createContext("/stop", new StopJob(executor));
        server.setExecutor(null);
        server.start();
        System.out.println("Server is listening on port " + port);

    }

    static class Ali implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();

            JdbcClient jdbcClient = DataSourceConnectionService.getJdbcClient();
            List<Map<String, Object>> res = jdbcClient.sql("select * from test").query().listOfRows();

            sendResponse(exchange, gson.toJson(res));
        }
    }

    static class Saeid implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
            sendResponse(exchange, "Saeid called from " + clientIp);
        }
    }

    static class StartJob implements HttpHandler {

        private ScheduledExecutorService executor;

        public StartJob(ScheduledExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            executor.scheduleAtFixedRate(() -> {
                System.out.println(new Random().nextInt() * 1000);
            }, 0, 1, TimeUnit.SECONDS);
            sendResponse(exchange, "job has been started");
        }
    }

    static class StopJob implements HttpHandler {
        private ScheduledExecutorService executor;

        public StopJob(ScheduledExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            executor.shutdown();
            sendResponse(exchange, "job has been stopped");
        }
    }


    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
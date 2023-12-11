package ir.mci;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/ali", new RunDBQuery());

        server.createContext("/start-job", new StartJob(executor));
        server.createContext("/stop-job", new StopJob(executor));
        server.setExecutor(null);
        server.start();
        System.out.println("Server is listening on port " + port);

    }

}
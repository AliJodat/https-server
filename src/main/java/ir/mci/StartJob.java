package ir.mci;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartJob implements HttpHandler {

    private ScheduledExecutorService executor;

    public StartJob(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        executor.scheduleAtFixedRate(() -> {
            System.out.println(new Random().nextInt() * 1000);
        }, 0, 1, TimeUnit.SECONDS);
        Utils.generateResponse(exchange, "job has been started");
    }

}

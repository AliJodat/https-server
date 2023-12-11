package ir.mci;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

public class StopJob implements HttpHandler {

    private ScheduledExecutorService executor;

    public StopJob(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        executor.shutdown();
        Utils.generateResponse(exchange, "job has been stopped");
    }

}

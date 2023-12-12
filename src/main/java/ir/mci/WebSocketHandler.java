package ir.mci;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketHandler implements HttpHandler {

    private ScheduledExecutorService executor;

    public WebSocketHandler(ScheduledExecutorService executor) {
        this.executor = executor;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            handleWebSocketRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleWebSocketRequest(HttpExchange exchange) throws IOException {
        // Upgrade the HTTP connection to a WebSocket connection
        exchange.sendResponseHeaders(101, 0); // Switching Protocols

        // WebSocket connection established, now send data over the socket every 5 seconds
        handleSocketDataSending();

        // Do not close the exchange as this is a WebSocket connection
    }

    private void handleSocketDataSending() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String serverAddress = localhost.getHostAddress();
        int serverPort = 8000; // Replace with the actual server port

        try {
            Socket socket = new Socket(serverAddress, serverPort);


            // Sample data to send
            executor.scheduleAtFixedRate(() -> {
                try {
                    OutputStream outputStream = socket.getOutputStream();

                    // Convert the string to bytes and send it
                    byte[] dataBytes = new byte[0];
                    Random random = new Random();
                    int i = random.nextInt(1, 999);
                    dataBytes = (i + "").getBytes("UTF-8");
                    outputStream.write(dataBytes);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }, 0, 1, TimeUnit.SECONDS);


            System.out.println("Data sent to the server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


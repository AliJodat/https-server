package ir.mci;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Utils {

    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void generateResponse(HttpExchange exchange, String response) throws IOException {
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        if (!ValidIPs.IP_LIST.contains(clientIp)) {
            exchange.sendResponseHeaders(401, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write("Access denied!".getBytes());
            os.close();
            return;
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

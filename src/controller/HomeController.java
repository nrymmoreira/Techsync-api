package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HomeController implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {
            String json = "{ api: \"TechSync\"; version: \"1.0.0\"}";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
        } else {
            String msg = "{\"erro\": \"Método não suportado\"}";
            exchange.sendResponseHeaders(405, msg.getBytes().length);
            exchange.getResponseBody().write(msg.getBytes());
            exchange.close();
        }
    }
}

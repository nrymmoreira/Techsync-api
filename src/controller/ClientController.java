package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientService;
import model.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ClientController implements HttpHandler {
    private ClientService service = new ClientService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {
            List<Client> clients = service.getTodos();
            String json = Client.toJsonList(clients);

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

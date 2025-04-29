import com.sun.net.httpserver.HttpServer;
import routes.Router;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        Router.settingRotas(server);
        server.setExecutor(null);
        System.out.println("Servidor rodando em http://localhost:8080");
        server.start();
    }
}

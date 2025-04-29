package routes;

import com.sun.net.httpserver.HttpServer;
import controller.ClientController;
import controller.HomeController;

public class Router {
    public static void settingRotas(HttpServer server) {
        server.createContext("/", new HomeController());
        server.createContext("/clients", new ClientController());
    }
}

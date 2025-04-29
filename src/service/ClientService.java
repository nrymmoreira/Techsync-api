package service;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static List<Client> clients = new ArrayList<>();

    static {
        clients.add(new Client(1, "João"));
        clients.add(new Client(2, "Maria"));
    }

    public List<Client> getTodos() {
        return clients;
    }
}

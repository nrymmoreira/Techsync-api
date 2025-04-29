package model;

import java.util.List;

public class Client {
    private int id;
    private String nome;

    public Client(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    public static String toJsonList(List<Client> clients) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            sb.append(String.format("{\"id\": %d, \"nome\": \"%s\"}", c.getId(), c.getNome()));
            if (i < clients.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}

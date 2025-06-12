package br.com.techsync.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {

    private static final String LOG_FILE_PATH = "logins.csv";
    private static final String HEADER = "data_hora,usuario,ip,resultado,motivo";

    public LogService() {
        criarArquivoComCabecalhoSeNaoExistir();
    }

    public void registrarLogin(String usuario, String ip, boolean sucesso, String motivo) {
        String resultado = sucesso ? "SUCESSO" : "FALHA";
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String linha = String.format("%s,%s,%s,%s,%s", dataHora, usuario, ip, resultado, motivo == null ? "" : motivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

    private void criarArquivoComCabecalhoSeNaoExistir() {
        try {
            Path path = Paths.get(LOG_FILE_PATH);
            if (!Files.exists(path)) {
                Files.write(path, (HEADER + System.lineSeparator()).getBytes());
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo de log: " + e.getMessage());
        }
    }
}

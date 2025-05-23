package br.com.techsync.models;

import jakarta.persistence.*;

@Entity
@Table(name = "T_TS_COMPANY")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true, length = 15)
    private long cnpj;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "currency", nullable = false, length = 3 )
    private String currency;

    @Column(name = "timezone", nullable = false, length = 5)
    private String timezone;

    // Construtores

    public Empresa() {}

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}


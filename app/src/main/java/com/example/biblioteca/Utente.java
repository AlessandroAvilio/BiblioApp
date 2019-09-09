package com.example.biblioteca;

import java.util.ArrayList;
import java.util.Date;

public class Utente implements CambiaTipoInOro, ImpostaAdmin{
    private String nome, cognome;
    private String codiceFiscale;
    private Date nascita;
    private ArrayList<Integer> idLibro = new ArrayList<>();
    private String tipoUtenza;
    private static int count = 0;
    private int countPrestiti;
    private int tesseraPunti;
    private String email;
    private String password;
    private String permessi;

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Date getNascita() {
        return nascita;
    }

    public void setNascita(Date nascita) {
        this.nascita = nascita;
    }

    public ArrayList<Integer> getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(ArrayList<Integer> idLibro) { //Integer = WrapperClass
        this.idLibro = idLibro;
    }

    public String getTipoUtenza() {
        return tipoUtenza;
    }

    public void setTipoUtenza(String tipoUtenza) {
        this.tipoUtenza = tipoUtenza;
    }

    public void setCountPrestiti(int countPrestiti) {
        this.countPrestiti = countPrestiti;
    }

    public int getCountPrestiti() {
        return countPrestiti;
    }

    public int getTesseraPunti() {
        return tesseraPunti;
    }

    public void setTesseraPunti(int tesseraPunti) {
        this.tesseraPunti = tesseraPunti;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermessi() {
        return permessi;
    }

    public void setPermessi(String permessi) {
        this.permessi = permessi;
    }

    @Override
    public void cambiaInOro(String tipoUtenza) {
        this.tipoUtenza = "Oro";
    }

    @Override
    public void impostaAdmin(String permessi) {
        this.permessi = "Admin";
    }
}

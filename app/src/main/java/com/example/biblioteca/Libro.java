package com.example.biblioteca;

public class Libro {
    private String autore, titolo;
    private int nCopieIn;
    private int nCopieOut;
    private String genere;
    private int annoPubblicazione;

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getnCopieIn() {
        return nCopieIn;
    }

    public void setnCopieIn(int nCopieIn) {
        this.nCopieIn = nCopieIn;
    }

    public int getnCopieOut() {
        return nCopieOut;
    }

    public void setnCopieOut(int nCopieOut) {
        this.nCopieOut = nCopieOut;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
}

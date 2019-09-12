package com.example.biblioteca;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
    private Button aggiungiLibro;
    private Button eliminaLibro;
    private Button ricercaLibro;
    private Button mostraCatalogo;
    private Button prestitoLibro;
    private Button cancellaBtn;
    private Button cercaUtenteBtn;
    private TextView userMail;
    Intent i;

    BiblioDB biblioDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        biblioDB = new BiblioDB(this);

        userMail = findViewById(R.id.emailUserField);
        i = getIntent();
        String mailExtra = i.getStringExtra("Utente");
        userMail.setText(mailExtra);


        aggiungiLibro = findViewById(R.id.aggiungiLibro);
        aggiungiLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriAggiungiLibro();
            }
        });


        eliminaLibro = findViewById(R.id.eliminaLibro);
        eliminaLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriEliminaLibro();
            }
        });

        ricercaLibro = findViewById(R.id.ricercaLibro);
        ricercaLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriRicercaLibro();
            }
        });

        cercaUtenteBtn = findViewById(R.id.cercaUtenteBtn);
        cercaUtenteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriCercaUtente();
            }
        });

        mostraCatalogo = findViewById(R.id.mostraCatalogoBtn);
        mostraCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = biblioDB.mostraCatalogo();
                if(cur.getCount() == 0){
                    Toast cerca = Toast.makeText(HomePageActivity.this, "Questo libro non è presente nel catalogo", Toast.LENGTH_SHORT);
                    cerca.show();
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(cur.moveToNext()){
                        buffer.append("titolo : " + cur.getString(2) + "\n");
                        buffer.append("autore : " + cur.getString(1) + "\n");
                        buffer.append("copie prestate : " + cur.getString(3) + "\n");
                        buffer.append("copie presenti : " + cur.getString(4) + "\n");
                        buffer.append("anno pubblicazione : " + cur.getString(5) + "\n");
                        buffer.append("genere : " + cur.getString(6) + "\n\n");
                    }

                    showMessage("Info", buffer.toString());
                }
            }
        });

        prestitoLibro = findViewById(R.id.prestitoBtn);
        prestitoLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriPrestitoLibro();
            }
        });


        cancellaBtn = findViewById(R.id.cancBtn);
        cancellaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biblioDB.cancellaTabella();
            }
        });

        Cursor cursor = biblioDB.restituisciPermessi(mailExtra);
        cursor.moveToFirst();
        String permessi = cursor.getString(0);
        if(permessi.equals("Admin")){
            aggiungiLibro.setVisibility(View.VISIBLE);
            eliminaLibro.setVisibility(View.VISIBLE);
            prestitoLibro.setVisibility(View.INVISIBLE);
            cercaUtenteBtn.setVisibility(View.VISIBLE);
        }
    }

    public void apriAggiungiLibro(){
        Intent intent = new Intent(this, NuovoLibroActivity.class);
        startActivity(intent);
    }

    public void apriEliminaLibro(){
        Intent intent = new Intent(this, EliminaLibroActivity.class);
        startActivity(intent);
    }

    public void apriRicercaLibro(){
        Cursor cursor = biblioDB.restituisciCodiceFiscale(userMail.getText().toString().trim());
        cursor.moveToFirst();
        String cf = cursor.getString(0);
        Intent intent = new Intent(this, RicercaTitoloActivity.class);
        intent.putExtra("CF", cf);
        startActivity(intent);
    }

    public void apriPrestitoLibro(){
        Intent intent = new Intent(this, PrestitoLibroActivity.class);
        intent.putExtra("Utente", userMail.getText().toString());
        startActivity(intent);
    }

    public void apriCercaUtente(){
        Intent intent = new Intent(this, CercaUtenteActivity.class);
        startActivity(intent);
    }


    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

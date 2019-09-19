package com.example.biblioteca;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CercaUtenteActivity extends AppCompatActivity {
    private EditText ricercaUtenteDaLibroField;
    private Button ricercaUtenteDaLibroBtn;
    private String mail;
    private Intent i;
    private BiblioDB biblioDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_utente);
        biblioDB = new BiblioDB(this);

        i = getIntent();
        mail = i.getStringExtra("Utente");

        ricercaUtenteDaLibroField = findViewById(R.id.ricercaLibroUtente);
        ricercaUtenteDaLibroBtn = findViewById(R.id.ricercaLibroUtenteBtn);

        ricercaUtenteDaLibroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titolo = ricercaUtenteDaLibroField.getText().toString().trim();
                boolean res = biblioDB.verificaEsistenzaTitolo(titolo);
                if(res == true){
                    Cursor cursor = biblioDB.mostraUtenti(titolo);

                    StringBuffer buffer = new StringBuffer();
                    while(cursor.moveToNext()){
                        buffer.append("nome : " + cursor.getString(0) + "\n");
                        buffer.append("cognome : " + cursor.getString(1) + "\n");
                        buffer.append("codice fiscale : " + cursor.getString(2) + "\n");
                        buffer.append("tipo utenza : " + cursor.getString(3) + "\n");
                        buffer.append("genere : " + cursor.getString(6) + "\n\n");
                    }

                    if(cursor.getCount() == 0){
                        Toast titolo_non_trovato = Toast.makeText(CercaUtenteActivity.this, "Nessun utente registrato è in possesso di una copia di questo libro.", Toast.LENGTH_SHORT);
                        titolo_non_trovato.show();
                    }else {

                        showMessage("Info", buffer.toString());
                    }
                }else{
                    Toast titolo_non_trovato = Toast.makeText(CercaUtenteActivity.this, "Titolo non presente nel catalogo", Toast.LENGTH_SHORT);
                    titolo_non_trovato.show();
                }
            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

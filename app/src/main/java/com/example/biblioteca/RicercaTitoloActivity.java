package com.example.biblioteca;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RicercaTitoloActivity extends AppCompatActivity {
    private EditText ricerca;
    private Button ricercaBtn;
    private TextView titolo;
    private TextView autore;
    private TextView copiePrestate;
    private TextView copiePresenti;
    private TextView genere;
    private TextView annopubblicazione;
    LibriDB libriDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_titolo);
        libriDB = new LibriDB(this);

        ricerca = findViewById(R.id.ricercaLibroField);
        ricercaBtn = findViewById(R.id.cercaBtn);
        titolo = findViewById(R.id.titleField);
        autore = findViewById(R.id.autoreTextView);
        copiePrestate = findViewById(R.id.nCopieOutTextView);
        copiePresenti = findViewById(R.id.nCopieInTextView);
        genere = findViewById(R.id.genereTextView);
        annopubblicazione = findViewById(R.id.annoTextView);

        ricercaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = libriDB.ricercaTitolo(ricerca.getText().toString().trim());
                if(cur.getCount() == 0){
                    Toast cerca = Toast.makeText(RicercaTitoloActivity.this, "Questo libro non è presente nel catalogo", Toast.LENGTH_SHORT);
                    cerca.show();
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(cur.moveToNext()){
                        buffer.append("titolo : " + cur.getString(0) + "\n");
                        buffer.append("autore : " + cur.getString(1) + "\n");
                        buffer.append("copie prestate : " + cur.getString(2) + "\n");
                        buffer.append("copie presenti : " + cur.getString(3) + "\n");
                        buffer.append("anno pubblicazione : " + cur.getString(4) + "\n");
                        buffer.append("genere : " + cur.getString(5));
                    }

                    showMessage("Info", buffer.toString());
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

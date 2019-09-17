package com.example.biblioteca;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RitiroLibriActivity extends AppCompatActivity {
    private EditText ricerca;
    private Button ricercaBtn;
    private TextView userMail;
    private String mail;
    BiblioDB biblioDB;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ritiro_libri);
        biblioDB = new BiblioDB(this);
        userMail = findViewById(R.id.emailUserField_2);
        i = getIntent();
        userMail.setText(i.getStringExtra("Utente"));
        mail = i.getStringExtra("Utente");


        ricerca = findViewById(R.id.titoloLibroDaRitirare);
        ricercaBtn = findViewById(R.id.ritiroLibroBtn);

        ricercaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = biblioDB.ricercaTitolo(ricerca.getText().toString().trim());
                if(cur.getCount() == 0) {
                    Toast cerca = Toast.makeText(RitiroLibriActivity.this, "Questo libro non è presente nel catalogo", Toast.LENGTH_SHORT);
                    cerca.show();
                }else{
                    boolean checkEsistenzaID = biblioDB.controllaPresenzaLibro(mail, ricerca.getText().toString());

                    if(checkEsistenzaID == true){
                        int val3 = biblioDB.ritiraLibro(ricerca.getText().toString(), mail);
                        int val1 = biblioDB.diminuisciCopiePrestate(ricerca.getText().toString());
                        int val2 = biblioDB.aumentaCopiePresenti(ricerca.getText().toString());

                        if ((val1 > 0) && (val2 > 0) && (val3 > 0)) {
                            Toast ritiro_effettuato = Toast.makeText(RitiroLibriActivity.this, "Ritiro effettuato", Toast.LENGTH_SHORT);
                            ritiro_effettuato.show();
                        }else if(val3 == 0){
                            Toast errore_ritiro = Toast.makeText(RitiroLibriActivity.this, "Errore. Riprovare", Toast.LENGTH_SHORT);
                            errore_ritiro.show();
                        }
                    }else{
                        Toast libro_non_presente = Toast.makeText(RitiroLibriActivity.this, "L'utente non è in possesso di nessuna copia di questo libro.", Toast.LENGTH_SHORT);
                        libro_non_presente.show();
                    }
                }
            }
        });
    }
}


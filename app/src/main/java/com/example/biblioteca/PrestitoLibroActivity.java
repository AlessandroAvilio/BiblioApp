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

public class PrestitoLibroActivity extends AppCompatActivity {
    private EditText cercaLibroDaPrestare;
    private Button prestaLibroBtn;
    private Intent i;
    private TextView userMail;
    private Utente utente;
    private BiblioDB biblioDB;
    private String mail;
    private int counterPrestitiEffettuati = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestito_libro);
        biblioDB = new BiblioDB(this);
        userMail = findViewById(R.id.mailUserField_2);
        i = getIntent();
        userMail.setText(i.getStringExtra("Utente"));
        mail = i.getStringExtra("Utente");
        utente = new Utente();

        cercaLibroDaPrestare = findViewById(R.id.titoloDaRitirare);
        prestaLibroBtn = findViewById(R.id.prestitoBtn);


        prestaLibroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = biblioDB.ricercaTitolo(cercaLibroDaPrestare.getText().toString().trim());
                int libriPresenti = biblioDB.getCounterCopiePresenti(cercaLibroDaPrestare.getText().toString().trim());
                if(cur.getCount() == 0){
                    Toast cerca = Toast.makeText(PrestitoLibroActivity.this, "Questo libro non è presente nel catalogo", Toast.LENGTH_SHORT);
                    cerca.show();
                }else if(libriPresenti == 0){
                    Toast noCopies = Toast.makeText(PrestitoLibroActivity.this, "Non sono più disponibili copie di questo libro", Toast.LENGTH_SHORT);
                    noCopies.show();
                    }
                    else{

                    boolean checkEsistenzaID = biblioDB.controllaPresenzaLibro(mail, cercaLibroDaPrestare.getText().toString());

                    if(checkEsistenzaID == false){
                        int val1 = biblioDB.effettuaPrestito(mail);
                        int val2 = biblioDB.aumentaCopiePrestate(cercaLibroDaPrestare.getText().toString());
                        int val3 = biblioDB.diminuisciCopiePresenti(cercaLibroDaPrestare.getText().toString());
                        long val4 = biblioDB.associaLibroAUtente(cercaLibroDaPrestare.getText().toString().trim(), mail);
                        if ((val1 > 0) && (val2 > 0) && (val3 > 0) && (val4 > 0)) {
                            Toast inserimento = Toast.makeText(PrestitoLibroActivity.this, "Prestito effettuato", Toast.LENGTH_SHORT);
                            inserimento.show();

                            String utenza = biblioDB.getTipoUtenza(mail);

                            if(utenza.equals("Oro")){
                                biblioDB.aumentaTesseraPunti(mail);
                            }

                            counterPrestitiEffettuati = biblioDB.getCounterPrestiti(mail);

                            if(counterPrestitiEffettuati == 5){
                                int conferma = biblioDB.impostaUtenzaInOro(mail);
                                if(conferma > 0){
                                    Toast cambioTIpoUtenza = Toast.makeText(PrestitoLibroActivity.this, "Congratulazioni! Hai appena ottenuto la tua tessera punti con il tuo quindi prestito!", Toast.LENGTH_SHORT);
                                    cambioTIpoUtenza.show();
                                }
                            }

                        } else {
                            Toast inserimento = Toast.makeText(PrestitoLibroActivity.this, "Errore", Toast.LENGTH_SHORT);
                            inserimento.show();
                        }
                    }else{
                        Toast inserimento = Toast.makeText(PrestitoLibroActivity.this, "L'utente possiede già una copia di questo libro.", Toast.LENGTH_SHORT);
                        inserimento.show();
                    }
                }
            }
        });
    }
}

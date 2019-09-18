package com.example.biblioteca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EliminaLibroActivity extends AppCompatActivity {
    private EditText ricercaLibro;
    private Button eliminaBtn;
    BiblioDB biblioDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina_libro);
        biblioDB = new BiblioDB(this);

        ricercaLibro = findViewById(R.id.ricercaLibroPrestitoField);
        eliminaBtn = findViewById(R.id.cercaLibroPrestitoBtn);



        eliminaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titolo = ricercaLibro.getText().toString().trim();
                boolean res = biblioDB.verificaEsistenzaTitolo(titolo);
                if(res == true) {
                    Integer val = biblioDB.eliminaLibro(titolo);
                    if (val > 0) {
                        Toast dismissione_avvenuta = Toast.makeText(EliminaLibroActivity.this, "Libro dismesso", Toast.LENGTH_SHORT);
                        dismissione_avvenuta.show();
                    } else {
                        Toast errore_dismissione = Toast.makeText(EliminaLibroActivity.this, "Attenzione! Non tutte le copie di questo libro sono state restituite.", Toast.LENGTH_SHORT);
                        errore_dismissione.show();
                    }
                }else{
                    Toast titolo_non_trovato = Toast.makeText(EliminaLibroActivity.this, "Titolo non presente nel catalogo", Toast.LENGTH_SHORT);
                    titolo_non_trovato.show();
                }
            }
        });

    }


}

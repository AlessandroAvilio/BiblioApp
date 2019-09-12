package com.example.biblioteca;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NuovoLibroActivity extends AppCompatActivity {
    private EditText campoTitolo;
    private EditText campoAutore;
    private EditText campoCopiePresenti;
    private EditText campoGenere;
    private EditText campoAnno;
    private Button nuovoLibro;

    BiblioDB biblioDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_libro);
        biblioDB = new BiblioDB(this);

        campoTitolo = findViewById(R.id.titleField);
        campoAutore = findViewById(R.id.authorField);
        campoCopiePresenti= findViewById(R.id.copieInField);
        campoGenere = findViewById(R.id.genreField);
        campoAnno = findViewById(R.id.yearField);
        nuovoLibro = findViewById(R.id.confirmNewBookBtn);

        campoTitolo.addTextChangedListener(nuovoLibroTW);
        campoAutore.addTextChangedListener(nuovoLibroTW);
        campoCopiePresenti.addTextChangedListener(nuovoLibroTW);
        campoGenere.addTextChangedListener(nuovoLibroTW);
        campoAnno.addTextChangedListener(nuovoLibroTW);

        nuovoLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titolo = campoTitolo.getText().toString().trim();
                String autore = campoAutore.getText().toString().trim();
                int copieIn = Integer.parseInt(campoCopiePresenti.getText().toString().trim());
                String genere = campoGenere.getText().toString().trim();
                int anno = Integer.parseInt(campoAnno.getText().toString().trim());

                Libro libro = new Libro();

                libro.setTitolo(titolo);
                libro.setAutore(autore);
                libro.setnCopieOut(0);
                libro.setnCopieIn(copieIn);
                libro.setGenere(genere);
                libro.setAnnoPubblicazione(anno);


                long val = biblioDB.inserisciLibro(libro);
                if(val > 0){
                    Toast inserimento = Toast.makeText(NuovoLibroActivity.this, "Libro inserito", Toast.LENGTH_SHORT);
                    inserimento.show();
                    //Intent intent = new Intent(NuovoLibroActivity.this, HomePageActivity.class);
                    //startActivity(intent);
                }else{
                    Toast inserimento = Toast.makeText(NuovoLibroActivity.this, "Libro NON inserito", Toast.LENGTH_SHORT);
                    inserimento.show();
                }
            }
        });
    }

    private TextWatcher nuovoLibroTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = campoTitolo.getText().toString().trim();
            String author = campoAutore.getText().toString().trim();
            String copiesIn = campoCopiePresenti.getText().toString().trim();
            String genre = campoGenere.getText().toString().trim();
            String year = campoAnno.getText().toString().trim();

            nuovoLibro.setEnabled(!title.isEmpty() && !author.isEmpty() && !copiesIn.isEmpty() && !genre.isEmpty() && !year.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}

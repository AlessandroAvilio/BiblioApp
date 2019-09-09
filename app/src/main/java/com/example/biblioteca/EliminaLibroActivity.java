package com.example.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EliminaLibroActivity extends AppCompatActivity {
    private EditText ricercaLibro;
    private Button eliminaBtn;
    LibriDB libriDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina_libro);
        libriDB = new LibriDB(this);

        ricercaLibro = findViewById(R.id.ricercaLibroField);
        eliminaBtn = findViewById(R.id.cercaBtn);

        ricercaLibro.addTextChangedListener(eliminaLibroTW);

        eliminaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titolo = ricercaLibro.getText().toString().trim();


                Integer val = libriDB.eliminaLibro(titolo);
                if(val > 0 ){
                    Toast dismissione = Toast.makeText(EliminaLibroActivity.this, "Libro dismesso", Toast.LENGTH_SHORT);
                    dismissione.show();
                    Intent intent = new Intent(EliminaLibroActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }else{
                    Toast dismissione = Toast.makeText(EliminaLibroActivity.this, "Errore durante la dismissione", Toast.LENGTH_SHORT);
                    dismissione.show();
                }
            }
        });

    }

    private TextWatcher eliminaLibroTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = ricercaLibro.getText().toString().trim();
            eliminaBtn.setEnabled(!title.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}

package com.example.biblioteca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RitiroLibriActivity extends AppCompatActivity {
    private EditText ricerca;
    private Button ricercaBtn;
    BiblioDB biblioDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ritiro_libri);
        biblioDB = new BiblioDB(this);

        ricerca = findViewById(R.id.titoloDaRitirare);
        ricercaBtn = findViewById(R.id.ritiroBtn);

        ricercaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

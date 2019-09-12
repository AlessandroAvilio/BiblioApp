package com.example.biblioteca;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RicercaTitoloActivity extends AppCompatActivity {
    private EditText ricerca;
    private Button ricercaBtn;
    BiblioDB biblioDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_titolo);
        biblioDB = new BiblioDB(this);

        ricerca = findViewById(R.id.ricercaLibroPrestitoField);
        ricercaBtn = findViewById(R.id.cercaLibroPrestitoBtn);

        ricercaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titolo = ricerca.getText().toString().trim();
                Cursor cur = biblioDB.ricercaTitolo(titolo);
                if(cur.getCount() == 0){
                    Toast cerca = Toast.makeText(RicercaTitoloActivity.this, "Questo libro non è presente nel catalogo", Toast.LENGTH_SHORT);
                    cerca.show();
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(cur.moveToNext()){
                        //String log = cur.getString(2);
                        //Log.d("PROVA", log);
                        buffer.append("titolo : " + cur.getString(2) + "\n");
                        buffer.append("autore : " + cur.getString(1) + "\n");
                        buffer.append("copie prestate : " + cur.getString(3) + "\n");
                        buffer.append("copie presenti : " + cur.getString(4) + "\n");
                        buffer.append("anno pubblicazione : " + cur.getString(5) + "\n");
                        buffer.append("genere : " + cur.getString(6));
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

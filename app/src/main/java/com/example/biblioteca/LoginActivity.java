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

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button registraUtente;
    private Button registraAdmin;
    private Button cancellaUtenze;
    private Button instantAccess;

    BiblioDB uDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uDb = new BiblioDB(this);

        email = findViewById(R.id.copieInField);
        password = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);

        email.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);


        registraUtente = findViewById(R.id.registraUtenteBtn);
        registraUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriRegistraUtente();
            }
        });

        registraAdmin = findViewById(R.id.registraAdminBtn);
        registraAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriRegistraAdmin();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailLogin = findViewById(R.id.copieInField);
                String emLogin = emailLogin.getText().toString();
                EditText passwordLogin = findViewById(R.id.passwordField);
                String passLogin = passwordLogin.getText().toString();

                Boolean result = uDb.checkUtente(emLogin, passLogin);
                if(result == true){
                    apriHomePage();
                }else{
                    Toast notLogin = Toast.makeText(LoginActivity.this, "Errore. Riprova", Toast.LENGTH_SHORT);
                    notLogin.show();
                }

            }
        });

        cancellaUtenze = findViewById(R.id.cancellaUtenzeBtn);
        cancellaUtenze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancella();
            }
        });
        instantAccess = findViewById(R.id.instantAccess);
        instantAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriHomePage();
            }
        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after) {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            loginBtn.setEnabled(!mail.isEmpty() && !pass.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void apriHomePage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("Utente", email.getText().toString());
        startActivity(intent);
    }

    public void apriRegistraUtente(){
        Intent intent = new Intent(this, RegistrazioneUtenteActivity.class);
        startActivity(intent);
    }

    public void apriRegistraAdmin(){
        Intent intent = new Intent(this, RegistrazioneUtenteActivity.class);
        intent.putExtra("Bottone", "admin");
        startActivity(intent);
    }

    public void cancella(){
        BiblioDB db = new BiblioDB(this);
        db.cancellaUtenze();
    }
}

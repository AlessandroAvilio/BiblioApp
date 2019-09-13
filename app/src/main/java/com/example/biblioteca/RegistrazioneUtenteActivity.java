package com.example.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneUtenteActivity extends AppCompatActivity {
    private EditText campoNome;
    private EditText campoCogome;
    //private EditText campoNascita;
    private EditText campoCodiceFiscale;
    private EditText campoMail;
    private EditText campoPassword;
    private EditText campoConfermaPassword;
    private Button conferma;


    BiblioDB biblioDB;      //crea un oggetto di classe BiblioDB. Necessario per poter inserire una nuova utenza tramite il metodo inserisciUtente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_utente);
        biblioDB = new BiblioDB(this);


        campoNome = findViewById(R.id.titleField);
        campoCogome = findViewById(R.id.authorField);
        campoCodiceFiscale = findViewById(R.id.genreField);
        campoCodiceFiscale.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        //campoNascita = findViewById(R.id.dateField);
        campoMail = findViewById(R.id.copieInField);          //ASSOCIA I VALORI DICHIARATI SOPRA AGLI ELEMENTI PRESENTI NEL XML
        campoPassword = findViewById(R.id.passwordField);
        campoConfermaPassword = findViewById(R.id.confirmPassField);
        conferma = findViewById(R.id.confirmNewBookBtn);


        campoNome.addTextChangedListener(registrazioneTextWatcher);
        campoCogome.addTextChangedListener(registrazioneTextWatcher);
        campoCodiceFiscale.addTextChangedListener(registrazioneTextWatcher);
        //campoNascita.addTextChangedListener(registrazioneTextWatcher);
        campoMail.addTextChangedListener(registrazioneTextWatcher);
        campoPassword.addTextChangedListener(registrazioneTextWatcher);
        campoConfermaPassword.addTextChangedListener(registrazioneTextWatcher);

        conferma.setOnClickListener(new View.OnClickListener() {        //Una volta cliccato il tasto di conferma registrazione, esequi questo:
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString().trim();
                String cognome = campoCogome.getText().toString().trim();
                String codicefiscale = campoCodiceFiscale.getText().toString().trim();
                String email = campoMail.getText().toString().trim();
                String password = campoPassword.getText().toString().trim();
                String confermaPassword = campoConfermaPassword.getText().toString().trim();
                Utente utente = new Utente();

                if((password.equals(confermaPassword))&&(validaPassword(password))){
                    //ArrayList<String> arr = new ArrayList<String>();
                    //arr.add("");
                    utente.setNome(nome);
                    utente.setCognome(cognome);
                    utente.setCodiceFiscale(codicefiscale);
                    utente.setTipoUtenza("Argento");        //imposta gli attributi di utente tramite le stringhe definite sopra. Alcuni attributi hanno valori impostati di default
                    utente.setCountPrestiti(0);
                    utente.setTesseraPunti(0);
                    utente.setEmail(email);
                    utente.setPassword(password);
                    utente.setPermessi("User");
                    Intent i = getIntent();
                    String impostaPermessi = i.getStringExtra("Bottone");
                    if (Objects.equals(impostaPermessi, "admin")){
                        utente.impostaAdmin(utente.getPermessi());
                    }

                    /*biblioDB.inerisciUtente(utente);
                    Cursor cursor = biblioDB.cercaUtente(utente.getCodiceFiscale());
                    if(cursor.getCount() == 0){
                        Toast inserimento = Toast.makeText(RegistrazioneUtenteActivity.this, "Utente NON registrato", Toast.LENGTH_SHORT);
                        inserimento.show();
                    }else{
                        Toast inserimento = Toast.makeText(RegistrazioneUtenteActivity.this, "Utente registrato", Toast.LENGTH_SHORT);
                        inserimento.show();
                        Intent intent = new Intent(RegistrazioneUtenteActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }*/

                   long val = biblioDB.inserisciUtente(utente);
                    if(val > 0){
                        Toast inserimento = Toast.makeText(RegistrazioneUtenteActivity.this, "Utente registrato", Toast.LENGTH_SHORT);
                        inserimento.show();
                        Intent intent = new Intent(RegistrazioneUtenteActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast inserimento = Toast.makeText(RegistrazioneUtenteActivity.this, "Utente NON registrato", Toast.LENGTH_SHORT);
                        inserimento.show();
                    }
                }else{
                    Toast inserimento = Toast.makeText(RegistrazioneUtenteActivity.this, "Le password non coincidono, oppure non inserita nel formato corretto", Toast.LENGTH_SHORT);
                    inserimento.show();
                }
            }
        });

    }


    private TextWatcher registrazioneTextWatcher = new TextWatcher() {      //metodo che permette di abilitare il tasto di conferma, una volta inseriti tutti i campi nel form
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after) {
            String name = campoNome.getText().toString().trim();
            String lastname = campoCogome.getText().toString().trim();
            String cf = campoCodiceFiscale.getText().toString().trim();
            //String birth = campoNascita.getText().toString().trim();
            String email = campoMail.getText().toString().trim();
            String password = campoPassword.getText().toString().trim();
            String confirmPass = campoConfermaPassword.getText().toString().trim();

            conferma.setEnabled(!name.isEmpty() && !lastname.isEmpty() && !cf.isEmpty() && /*!birth.isEmpty() &&*/ !email.isEmpty() && !password.isEmpty() && !confirmPass.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public boolean validaPassword(String password){
        Pattern pattern;
        Matcher matcher;

        final String passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!,$%.&])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(passPattern);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}

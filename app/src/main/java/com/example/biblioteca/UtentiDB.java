package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UtentiDB extends SQLiteOpenHelper {


    //DEFINIZIONE DATABASE "Utenti"
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Utenti.db";
    private static final String TABLE_NAME = "usertable";
    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_COGNOME = "cognome";
    private static final String COL_CODICEFISCALE = "codicefiscale";
    //private static Date COL_DATANASCITA;
    private static final String COL_TIPOUTENZA = "tipoutenza";
    private static final String COL_PRESTITI = "prestiti";
    private static final String COL_PUNTITESSERA = "puntitessera";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD= "password";
    private static final String COL_PERMESSI= "permessi";

    public UtentiDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usertable (id INTEGER PRIMARY KEY, nome TEXT, cognome TEXT, codicefiscale TEXT UNIQUE, tipoutenza TEXT, prestiti INTEGER, puntitessera INTEGER, email TEXT UNIQUE, password TEXT, permessi TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long inserisciUtente(Utente utente){     //Richiesto un oggetto di classe utente. Inserisce tutti gli attributi di utente all'interno delle colonne definite all'interno di values.put
        SQLiteDatabase db = this.getWritableDatabase();        //getWritableDatabase per creare oppure aprire un database per lettura e scrittura
        ContentValues values = new ContentValues();
        values.put(COL_NOME, utente.getNome());
        values.put(COL_COGNOME, utente.getCognome());
        values.put(COL_CODICEFISCALE, utente.getCodiceFiscale());
        //values.put(COL_DATANASCITA.toString(), utente.getNascita());
        values.put(COL_TIPOUTENZA, utente.getTipoUtenza());
        values.put(COL_PRESTITI, utente.getCountPrestiti());
        values.put(COL_PUNTITESSERA, utente.getTesseraPunti());
        values.put(COL_EMAIL, utente.getEmail());
        values.put(COL_PASSWORD, utente.getPassword());
        values.put(COL_PERMESSI, utente.getPermessi());
        long result = db.insert(TABLE_NAME, null, values);    //insert inserisce all'interno della tabella descritta il contenuto di values. Inserisce quindi una nuova riga di Utente nella usertable
        db.close();
        return result;
    }

    public boolean checkUtente (String email, String password){
        String[] colonne = {COL_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_EMAIL + "=?" + " and " + COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, colonne, selection, selectionArgs, null, null, null);
        //Il cursor "naviga" nella tabella specificata. Vengono inoltre specificate quali colonne controllare e quali elementi esaminare (In questo caso mail e password)
        //Una volta specificati in ingresso mail e password inseriti nel form di login, se questi coincidono con una coppia presente all'interno di un record nella tabella, allora assegna alla
        //variabile "count" il valore ottenuto dal getter di cursor (cursor.getCount()), che restituirà 1
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    public void cancellaTabella(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("usertable", null, null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

}

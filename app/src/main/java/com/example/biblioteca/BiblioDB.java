package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BiblioDB extends SQLiteOpenHelper {
    private static final String TAG = "BiblioDB";

    //DATABASE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Biblio.db";

    //TABELLA UTENTI
    private static final String USER_TABLE = "usertable";
    private static final String COL_NOME = "nome";
    private static final String COL_COGNOME = "cognome";
    private static final String COL_CODICEFISCALE = "codicefiscale";
    private static final String COL_TIPOUTENZA = "tipoutenza";
    private static final String COL_PRESTITI = "prestiti";
    private static final String COL_PUNTITESSERA = "puntitessera";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_PERMESSI = "permessi";
    public static String seperatoreStringa = "__,__";
    //TABELLA LIBRI
    private static final String BOOKS_TABLE = "booktable";
    private static final String COL_IDLIBRO = "id";
    private static final String COL_AUTORE = "autore";
    private static final String COL_TITOLO = "titolo";
    private static final String COL_COPIEPRESTATE = "copieprestate";
    private static final String COL_COPIEPRESENTI = "copiepresenti";
    private static final String COL_ANNOPUBBLICAZIONE = "annopubblicazione";
    private static final String COL_GENERE = "genere";
    //TABELLA MOLTI-A-MOLTI LIBRO-UTENTI
    private static final String USERS_BOOKS_TABLE = "users_books_table";
    private static final String COL_CFUTENTE = "codicefiscale";
    private static final String COL_ROWIDLIBRO = "id";

    public BiblioDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usertable (nome TEXT, cognome TEXT, codicefiscale TEXT PRIMARY KEY UNIQUE, tipoutenza TEXT, prestiti INTEGER, puntitessera INTEGER, email TEXT UNIQUE, password TEXT, permessi TEXT);");
        db.execSQL("CREATE TABLE booktable (id INTEGER PRIMARY KEY AUTOINCREMENT, autore TEXT, titolo TEXT, copiePrestate INTEGER, copiePresenti INTEGER, annoPubblicazione INTEGER, genere TEXT);");
        db.execSQL("CREATE TABLE users_books_table (codicefiscale TEXT, id INTEGER, FOREIGN KEY(codicefiscale) REFERENCES usertable(codicefiscale), FOREIGN KEY(id) REFERENCES booktable(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_BOOKS_TABLE);
        onCreate(db);
    }

    public long inserisciUtente(Utente utente) {     //Richiesto un oggetto di classe utente. Inserisce tutti gli attributi di utente all'interno delle colonne definite all'interno di values.put
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
        long result = db.insert(USER_TABLE, null, values);    //insert inserisce all'interno della tabella descritta il contenuto di values. Inserisce quindi una nuova riga di Utente nella usertable
        db.close();
        return result;
    }

    public long inserisciLibro(Libro libro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AUTORE, libro.getAutore());
        values.put(COL_TITOLO, libro.getTitolo());
        values.put(COL_COPIEPRESTATE, libro.getnCopieOut());
        values.put(COL_COPIEPRESENTI, libro.getnCopieIn());
        values.put(COL_ANNOPUBBLICAZIONE, libro.getAnnoPubblicazione());
        values.put(COL_GENERE, libro.getGenere());
        long result = db.insert(BOOKS_TABLE, null, values);
        db.close();
        return result;
    }

    public boolean checkUtente(String email, String password) {
        String[] colonne = {COL_CODICEFISCALE};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_EMAIL + "=?" + " and " + COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(USER_TABLE, colonne, selection, selectionArgs, null, null, null);
        //Il cursor "naviga" nella tabella specificata. Vengono inoltre specificate quali colonne controllare e quali elementi esaminare (In questo caso mail e password)
        //Una volta specificati in ingresso mail e password inseriti nel form di login, se questi coincidono con una coppia presente all'interno di un record nella tabella, allora assegna alla
        //variabile "count" il valore ottenuto dal getter di cursor (cursor.getCount()), che restituirà 1
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getCounterPrestiti(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("usertable", new String[]{COL_PRESTITI}, "email=?", new String[]{email}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int output = cursor.getInt(0);

        return output;
    }


    public int effettuaPrestito(String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        int countPrestiti = getCounterPrestiti(mail);
        int prestitiEffettuati = ++countPrestiti;
        ContentValues values = new ContentValues();
        values.put(COL_PRESTITI, prestitiEffettuati);
        int res = db.update("usertable", values, "email=?", new String[]{mail});
        db.close();
        return res;
    }


    public Integer eliminaLibro(String titolo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(BOOKS_TABLE, "titolo = ?", new String[]{titolo});
        db.close();
        return res;
    }

    public Cursor ricercaTitolo(String titolo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM booktable WHERE titolo=" + "'" + titolo + "'", null);
        //Cursor res = db.query("booktable", new String[]{COL_TITOLO}, "titolo=?", new String[]{titolo}, null, null, null);
        return res;
    }

    public Cursor mostraCatalogo() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from booktable", null);
        return res;
    }

    public void cancellaTabella() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("booktable", null, null);
        db.close();
    }

    public void cancellaUtenze(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("usertable", null, null);
        db.close();
    }

    public int getCounterCopiePrestate(String titolo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("booktable", new String[]{COL_COPIEPRESTATE}, "titolo=?", new String[]{titolo}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int output = cursor.getInt(0);

        return output;
    }

    public int contaCopiePrestate(String titolo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int countCopiePrestate = getCounterCopiePrestate(titolo);
        int copiePrestate = ++countCopiePrestate;
        ContentValues values = new ContentValues();
        values.put(COL_COPIEPRESTATE, copiePrestate);
        int res = db.update("booktable", values, "titolo=?", new String[]{titolo});
        db.close();
        return res;
    }

    public int getCounterCopiePresenti(String titolo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("booktable", new String[]{COL_COPIEPRESENTI}, "titolo=?", new String[]{titolo}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int output = cursor.getInt(0);

        return output;
    }

    public int contaCopiePresenti(String titolo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int countCopiePresenti = getCounterCopiePresenti(titolo);
        int copiePresenti = --countCopiePresenti;
        ContentValues values = new ContentValues();
        values.put(COL_COPIEPRESENTI, copiePresenti);
        int res = db.update("booktable", values, "titolo=?", new String[]{titolo});
        db.close();
        return res;
    }

    public Cursor restituisciPermessi(String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("usertable", new String[]{COL_PERMESSI}, "email=?", new String[]{mail}, null, null, null);
        return cursor;
    }

    public Cursor restituisciCodiceFiscale(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("usertable", new String[]{COL_CODICEFISCALE}, "email=?", new String[]{mail}, null, null, null);
        return cursor;
    }

    public long associaLibroAUtente(String titolo, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor userCursor = db.query("usertable", new String[]{COL_CODICEFISCALE}, "email=?", new String[]{mail}, null, null, null);
        userCursor.moveToFirst();
        String codiceFiscale = userCursor.getString(0);
        values.put(COL_CFUTENTE, codiceFiscale);
        Cursor cursor = db.query("booktable", new String[]{COL_IDLIBRO}, "titolo=?", new String[]{titolo}, null, null, null);
        cursor.moveToFirst();
        int idLibro = cursor.getInt(0);
        values.put(COL_ROWIDLIBRO, idLibro);
        long result = db.insert(USERS_BOOKS_TABLE, null, values);
        db.close();
        return result;
    }
}
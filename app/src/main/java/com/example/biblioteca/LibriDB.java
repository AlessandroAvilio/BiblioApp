package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibriDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Libri.db";
    private static final String TABLE_NAME = "bookstable";
    private static final String COL_ID = "id";
    private static final String COL_AUTORE = "autore";
    private static final String COL_TITOLO = "titolo";
    private static final String COL_COPIEPRESTATE = "copieprestate";
    private static final String COL_COPIEPRESENTI = "copiepresenti";
    private static final String COL_ANNOPUBBLICAZIONE = "annopubblicazione";
    private static final String COL_GENERE = "genere";



    public LibriDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE bookstable (id INTEGER PRIMARY KEY AUTOINCREMENT, titolo TEXT, autore TEXT, copieprestate INTEGER, copiepresenti INTEGER, annopubblicazione INTEGER, genere TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long inserisciLibro(Libro libro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AUTORE, libro.getAutore());
        values.put(COL_TITOLO, libro.getTitolo());
        values.put(COL_COPIEPRESTATE, libro.getnCopieOut());
        values.put(COL_COPIEPRESENTI, libro.getnCopieIn());
        values.put(COL_ANNOPUBBLICAZIONE, libro.getAnnoPubblicazione());
        values.put(COL_GENERE, libro.getGenere());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Integer eliminaLibro(String titolo){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, "titolo = ?", new String[]{titolo});
        db.close();
        return res;
    }

    public Cursor ricercaTitolo(String titolo){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from bookstable where titolo = "+ titolo, null);
        return res;
    }

    public Cursor mostraCatalogo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from bookstable", null);
        return res;
    }

    public void cancellaTabella(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bookstable", null, null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

}

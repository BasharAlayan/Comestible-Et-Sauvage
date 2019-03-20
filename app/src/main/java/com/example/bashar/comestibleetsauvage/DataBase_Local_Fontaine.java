package com.example.bashar.comestibleetsauvage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_Local_Fontaine extends SQLiteOpenHelper {

    private static final String TAG = "DataBase_Global";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataBase_Local";

    private static final String TABLE_NAME = "Fontaine_Table";

    private static final String COL1 = "id";
    private static final String COL2 = "nom";
    private static final String COL3 = "libelle";
    private static final String COL4 = "statut";
    private static final String COL5 = "image";
    private static final String COL6 = "latitude";
    private static final String COL7 = "longitude";

    private String LastId = "";

    public DataBase_Local_Fontaine(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //création de la table SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( "
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " VARCHAR(100) ,"
                + COL3 + " VARCHAR(100) ,"
                + COL4 + " VARCHAR(100) ,"
                + COL5 + " BLOB ,"
                + COL6 + " VARCHAR(100) ,"
                + COL7 + " VARCHAR(100) )";

        db.execSQL(createTable);
    }

    public void createTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COL2 + " VARCHAR(100) ,"
                + COL3 + " VARCHAR(100) ,"
                + COL4 + " VARCHAR(100) ,"
                + COL5 + " BLOB ,"
                + COL6 + " VARCHAR(100) ,"
                + COL7 + " VARCHAR(100) )";

        database.execSQL(createTable);
    }

    public void delete() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DROP TABLE " + TABLE_NAME;
        database.execSQL(query);
    }


    //Méthodes d'ajout,de modification et de supression dans la BDD locale
    public void updateTableLAT() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "ALTER TABLE " + TABLE_NAME + " ADD " + COL6 + " VARCHAR(100)";
        database.execSQL(query);
    }

    public void updateTableLON() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "ALTER TABLE " + TABLE_NAME + " ADD " + COL7 + " VARCHAR(100)";
        database.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older Table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create tables again
        onCreate(db);
    }

    public void addNewFontaine(Fontaine newFontaine) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL2, newFontaine.getNom());
        values.put(COL3, newFontaine.getLibelle());
        values.put(COL4, newFontaine.getStatut());
        values.put(COL5, newFontaine.getImage());


        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void addNewFontainePos(Fontaine newFontaine) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL2, newFontaine.getNom());
        values.put(COL3, newFontaine.getLibelle());
        values.put(COL4, newFontaine.getStatut());
        values.put(COL5, newFontaine.getImage());
        values.put(COL6, newFontaine.getLat());
        values.put(COL7, newFontaine.getLon());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void addLat(String last_id, String lat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 + " = " + lat + " WHERE " + COL1 + " = " + last_id;
        database.execSQL(query);

    }

    public void addLng(String last_id, String lng) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 + " = " + lng + " WHERE " + COL1 + " = " + last_id;
        database.execSQL(query);
    }


    public String getLastId() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT MAX( " + COL1 + " ) FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        while (data.moveToNext()) {
            LastId = data.getString(0);
        }
        return LastId;
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    //POURRAIT ETRE UTILISEE DANS LE FUTUR
    public Cursor numberRow() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }


    public Cursor noms() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }


    public Cursor libelles() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor id() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor status() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor lats() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL6 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor lngs() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT " + COL7 + " FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void DeleteRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = " DELETE FROM " + TABLE_NAME + " WHERE id = " + id;
        database.execSQL(query);
    }

    //POURRAIT ETRE UTILISEE DANS LE FUTUR
    public void deleteData() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        database.execSQL(query);
    }

    public int CountFontaine() {
        int planteCount = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            planteCount = cursor.getInt(0);
        }
        cursor.close();
        return planteCount;
    }
}
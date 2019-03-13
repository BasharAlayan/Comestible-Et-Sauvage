package com.example.bashar.comestibleetsauvage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Res extends SQLiteOpenHelper {

    private static final String TAG = "DataBase_Global";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataBase_Local";

    private static final String TABLE_NAME = "Plante_Table_Res";

    private static final String COL1 = "id";
    private static final String COL2 = "nom";
    private static final String COL3 = "libelle";
    private static final String COL4 = "statut";
    private static final String COL5 = "image";
    private static final String COL6 = "latitude";
    private static final String COL7 = "longitude";

    private String LastId = "";

    public Database_Res(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //création de la table SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COL1 + " INTEGER PRIMARY KEY ,"
                + COL2 + " VARCHAR(100) ,"
                + COL3 + " VARCHAR(100) ,"
                + COL4 + " VARCHAR(100) ,"
                + COL5 + " BLOB ,"
                + COL6 + " VARCHAR(100) ,"
                + COL7 + " VARCHAR(100) )";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older Table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create tables again
        onCreate(db);
    }

    public void createTable(){
        SQLiteDatabase database = this.getWritableDatabase();
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COL1 + " INTEGER PRIMARY KEY ,"
                + COL2 + " VARCHAR(100) ,"
                + COL3 + " VARCHAR(100) ,"
                + COL4 + " VARCHAR(100) ,"
                + COL5 + " BLOB ,"
                + COL6 + " VARCHAR(100) ,"
                + COL7 + " VARCHAR(100) )";

        database.execSQL(createTable);
    }
    public void addPlanteRes(Plante newPlante) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL1, newPlante.getId());
        values.put(COL2, newPlante.getNom());
        values.put(COL3, newPlante.getLibelle());
        values.put(COL4, newPlante.getStatut());
        values.put(COL5, newPlante.getImage());
        values.put(COL6, newPlante.getLat());
        values.put(COL7, newPlante.getLon());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }
/*
    public void addDataRes(String Id, String Nom, String Libelle, String Statut, byte[] Image, String lat, String lng) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL1, Id);
        values.put(COL2, Nom);
        values.put(COL3, Libelle);
        values.put(COL4, Statut);
        values.put(COL5, Image);
        values.put(COL6, lat);
        values.put(COL7, lng);

        database.insert(TABLE_NAME, null, values);
        database.close();
    }
*/
    public void InsertData(String Id, String Nom, String Libelle, String Statut, byte[] Image, String lat, String lng) {
        /*
        SQLiteDatabase database = this.getWritableDatabase();
        String InsertData = " INSERT INTO "+TABLE_NAME+" VALUES "+ " ( " +Id+" , "+Nom+ "," +Libelle+ "," +Statut+ "," +null+","+lat+","+lng+" )";
        database.execSQL(InsertData);
    */
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL1, Id);
        values.put(COL2, Nom);
        values.put(COL3, Libelle);
        values.put(COL4, Statut);
        values.put(COL5, Image);
        values.put(COL6, lat);
        values.put(COL7, lng);

        database.insert(TABLE_NAME, null, values);
        database.close();
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

    public int CountPlantes() {
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
    public void deleteData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String InsertData = " DELETE FROM "+TABLE_NAME ;
        database.execSQL(InsertData);

    }

}



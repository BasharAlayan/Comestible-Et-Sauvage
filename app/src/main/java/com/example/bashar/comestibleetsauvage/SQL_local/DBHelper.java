package com.example.bashar.comestibleetsauvage.SQL_local;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.bashar.comestibleetsauvage.Plante;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "bdcoach.db";
    public static final String Plante_TABLE_NAME = "plante";
    public static final String Plante_COLUMN_ID = "id";
    public static final String Plante_COLUMN_NomPlante = "NomPlante";
    public static final String Plante_COLUMN_LiballePlante = "LiballePlante";
    public static final String Plante_COLUMN_StatutPlante= "StatutPlante";
    public static final String Plante_COLUMN_Image = "Image";
    private HashMap hp;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {



        //test
        db.execSQL("create table plante("
                +"id  INTEGER PRIMARY KEY ,"
                +"NomPlante  Varchar NOT NULL,"
                +"LiballePlante Varchar NOT NULL ,"
                +"StatutPlante Varchar NOT NULL,"
                +"Image Varchar NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertPlante (String NomPlante, String LiballePlante, String StatutPlante, String Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Plante_COLUMN_NomPlante, NomPlante);
        contentValues.put(Plante_COLUMN_LiballePlante, LiballePlante);
        contentValues.put(Plante_COLUMN_StatutPlante, StatutPlante);
        contentValues.put(Plante_COLUMN_Image, Image);
        try{
            db.insert(Plante_TABLE_NAME, null, contentValues);
            return true;
        }
        catch (Exception E)
        {
            return false;
        }
    }
    public Plante getPlante(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from"+ Plante_TABLE_NAME +"where id="+id+"", null );
        String nom,liballe,statut,image;
        res.moveToFirst();
        nom = res.getString(res.getColumnIndex(Plante_COLUMN_NomPlante));
        liballe = res.getString(res.getColumnIndex(Plante_COLUMN_LiballePlante));
        statut = res.getString(res.getColumnIndex(Plante_COLUMN_StatutPlante));
        image = res.getString(res.getColumnIndex(Plante_COLUMN_Image));
        Plante plante = new Plante(nom,liballe,statut,"",image);
        return plante;
    }
    public ArrayList<Plante> getAllPlante() {
        ArrayList<Plante> plantes = new ArrayList<Plante>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ Plante_TABLE_NAME, null );
        String nom,liballe,statut,image;
        res.moveToFirst();
        nom = res.getString(res.getColumnIndex(Plante_COLUMN_NomPlante));
        liballe = res.getString(res.getColumnIndex(Plante_COLUMN_LiballePlante));
        statut = res.getString(res.getColumnIndex(Plante_COLUMN_StatutPlante));
        image = res.getString(res.getColumnIndex(Plante_COLUMN_Image));
        Plante plante = new Plante(nom,liballe,statut,"",image);
        plantes.add(plante);
        while(res.isAfterLast() == false){
            nom = res.getString(res.getColumnIndex(Plante_COLUMN_NomPlante));
            liballe = res.getString(res.getColumnIndex(Plante_COLUMN_LiballePlante));
            statut = res.getString(res.getColumnIndex(Plante_COLUMN_StatutPlante));
            image = res.getString(res.getColumnIndex(Plante_COLUMN_Image));
            plante = new Plante(nom,liballe,statut,"",image);
            plantes.add(plante);
            res.moveToNext();
        }
        return plantes;
    }
}

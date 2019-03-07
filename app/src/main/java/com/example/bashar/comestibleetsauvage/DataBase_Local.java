package com.example.bashar.comestibleetsauvage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DataBase_Local extends SQLiteOpenHelper {

    private static final String TAG= "DataBase_Global";
    private static final int DATABASE_VERSION= 1;

    private static final String DATABASE_NAME= "DataBase_Local";
    private static final String TABLE_NAME= "Plante_Table";


    private static final String COL1= "id";
    private static final String COL2= "nom";
    private static final String COL3= "libelle";
    private static final String COL4= "statut";
    private static final String COL5= "image";


    public DataBase_Local(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE " + TABLE_NAME +" ( "
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " VARCHAR(100) ,"
                + COL3 + " VARCHAR(100) ,"
                + COL4 + " VARCHAR(100) ,"
                + COL5 + " BLOB )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older Table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create tables again
        onCreate(db);
    }

    public void addNewPlante(Plante newPlante){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL2,newPlante.getNom());
        values.put(COL3,newPlante.getLibelle());
        values.put(COL4,newPlante.getStatut());
        values.put(COL5,newPlante.getImage());

        database.insert(TABLE_NAME,null,values);
        database.close();
    }

    public Cursor getData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor data = database.rawQuery(query,null);
        return data;
    }

    public Cursor a(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query="SELECT "+COL2+" FROM "+TABLE_NAME;
        Cursor data = database.rawQuery(query,null);
        return data;
    }


    public Cursor b(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query="SELECT "+COL3+" FROM "+TABLE_NAME;
        Cursor data = database.rawQuery(query,null);
        return data;
    }


    public Cursor c(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query="SELECT "+COL4+" FROM "+TABLE_NAME;
        Cursor data = database.rawQuery(query,null);
        return data;
    }


    public void deleteData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query="DELETE FROM "+TABLE_NAME;
        database.execSQL(query);

    }

    /*

    public byte[] getImageByid(int id){
        SQLiteDatabase database = this.getWritableDatabase();

        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        String[] select ={COL1,COL2,COL3,COL4,COL5};

        queryBuilder.setTables(TABLE_NAME);
        Cursor c= (Cursor) queryBuilder.query(DATABASE_NAME,select," id = ?",new int[] {id},null,null);

        byte[] result=null;
        if(c.moveToFirst()){
            do {
                result=c.getBlob(c.getColumnIndex(COL5));

            }while (c.moveToNext());
        }
        return result;
    }
*/


}

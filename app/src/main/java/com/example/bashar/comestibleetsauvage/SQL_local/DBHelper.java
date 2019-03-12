package com.example.bashar.comestibleetsauvage.SQL_local;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.example.bashar.comestibleetsauvage.App;
import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "bdcoach.db";

    private static final DBHelper ourInstance = new DBHelper();

    public static DBHelper getInstance()
    {
        return ourInstance;
    }

    private DBHelper()
    {
        super(App.context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + Plante.PLANTE_TABLE_NAME + "(" + Plante.PLANTE_COLUMN_ID + " TEXT PRIMARY KEY," + Plante.PLANTE_COLUMN_NOM_PLANTE + " TEXT NOT NULL," + Plante.PLANTE_COLUMN_LIBALLE_PLANTE + " TEXT NOT NULL ," + Plante.PLANTE_COLUMN_STATUT_PLANTE + " TEXT NOT NULL," + Plante.PLANTE_COLUMN_IMAGE + " TEXT NOT NULL," + Plante.PLANTE_COLUMN_LOCAL_IMAGE + " TEXT NOT NULL,"+ Plante.PLANTE_COLUMN_LOCATION + " TEXT NOT NULL," + Plante.PLANTE_COLUMN_SYNC + " TEXT NOT NULL);");
        db.execSQL("create table " + Fontaine.Fontaine_TABLE_NAME + "(" + Fontaine.Fontaine_COLUMN_ID + " TEXT PRIMARY KEY," + Fontaine.Fontaine_COLUMN_NOM_Fontaine + " TEXT NOT NULL," + Fontaine.Fontaine_COLUMN_LIBALLE_Fontaine + " TEXT NOT NULL ," + Fontaine.Fontaine_COLUMN_STATUT_Fontaine + " TEXT NOT NULL," + Fontaine.Fontaine_COLUMN_IMAGE + " TEXT NOT NULL," + Fontaine.Fontaine_COLUMN_LOCAL_IMAGE + " TEXT NOT NULL," + Fontaine.Fontaine_COLUMN_LOCATION + " TEXT NOT NULL," + Fontaine.Fontaine_COLUMN_SYNC + " TEXT NOT NULL);");
        // all create tables query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

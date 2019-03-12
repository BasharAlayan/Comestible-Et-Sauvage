package com.example.bashar.comestibleetsauvage.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.bashar.comestibleetsauvage.SQL_local.DBHelper;
import com.example.bashar.comestibleetsauvage.firebase.DataBaseManager;
import com.example.bashar.comestibleetsauvage.firebase.FilesController;
import com.example.bashar.comestibleetsauvage.firebase.FilesListener;

import java.util.ArrayList;

public class Fontaine
{
    public static final String Fontaine_TABLE_NAME = "fontaine";
    public static final String Fontaine_COLUMN_ID = "id";
    public static final String Fontaine_COLUMN_NOM_Fontaine = "nom_fontaine";
    public static final String Fontaine_COLUMN_LIBALLE_Fontaine = "liballe_fontaine";
    public static final String Fontaine_COLUMN_STATUT_Fontaine = "statut_fontaine";
    public static final String Fontaine_COLUMN_IMAGE = "image";
    public static final String Fontaine_COLUMN_LOCATION = "location";
    public static final String Fontaine_COLUMN_LOCAL_IMAGE = "local_image";
    public static final String Fontaine_COLUMN_SYNC = "sync";

    public String id;
    public String nom;
    public String libelle;
    public String statut;
    public String image;
    public String location;
    public String localImage;
    public String sync = "0";

    public Fontaine()
    {

    }

    public Fontaine(String id, String nom, String libelle, String statut, String image, String location)
    {
        this.id = id;
        this.nom = nom;
        this.libelle = libelle;
        this.statut = statut;
        this.image = image;
        this.location = location;
    }

    public Fontaine(String id, String nom, String libelle, String statut, String image, String location, String localImage)
    {
        this.id = id;
        this.nom = nom;
        this.libelle = libelle;
        this.statut = statut;
        this.image = image;
        this.localImage = localImage;
        this.location = location;
    }
    public void replace()
    {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Fontaine_COLUMN_ID, id);
        contentValues.put(Fontaine_COLUMN_NOM_Fontaine, nom);
        contentValues.put(Fontaine_COLUMN_LIBALLE_Fontaine, libelle);
        contentValues.put(Fontaine_COLUMN_STATUT_Fontaine, statut);
        contentValues.put(Fontaine_COLUMN_IMAGE, image);
        contentValues.put(Fontaine_COLUMN_LOCAL_IMAGE, localImage);
        contentValues.put(Fontaine_COLUMN_SYNC, sync);
        contentValues.put(Fontaine_COLUMN_LOCATION, location);
        db.replace(Fontaine_TABLE_NAME, null, contentValues);
    }
    public void insert()
    {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Fontaine_COLUMN_ID, id);
        contentValues.put(Fontaine_COLUMN_NOM_Fontaine, nom);
        contentValues.put(Fontaine_COLUMN_LIBALLE_Fontaine, libelle);
        contentValues.put(Fontaine_COLUMN_STATUT_Fontaine, statut);
        contentValues.put(Fontaine_COLUMN_IMAGE, image);
        contentValues.put(Fontaine_COLUMN_LOCAL_IMAGE, localImage);
        contentValues.put(Fontaine_COLUMN_SYNC, sync);
        contentValues.put(Fontaine_COLUMN_LOCATION, location);
        db.insert(Fontaine_TABLE_NAME, null, contentValues);
    }
    public void delete() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        db.execSQL("delete from " + Fontaine_TABLE_NAME + " where " + Fontaine_COLUMN_ID + "='" + this.id + "';");
    }
    public void getFontaine(String id)
    {
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Fontaine_TABLE_NAME + " where " + Fontaine_COLUMN_ID + "='" + id + "';", null);
        if (res.moveToFirst())
        {
            this.id = id;
            nom = res.getString(res.getColumnIndex(Fontaine_COLUMN_NOM_Fontaine));
            libelle = res.getString(res.getColumnIndex(Fontaine_COLUMN_LIBALLE_Fontaine));
            statut = res.getString(res.getColumnIndex(Fontaine_COLUMN_STATUT_Fontaine));
            image = res.getString(res.getColumnIndex(Fontaine_COLUMN_IMAGE));
            localImage = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCAL_IMAGE));
            sync = res.getString(res.getColumnIndex(Fontaine_COLUMN_SYNC));
            location = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCATION));
        }
    }
    public static ArrayList<Fontaine> getAllFontaine()
    {
        ArrayList<Fontaine> fontaines = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Fontaine_TABLE_NAME + ";", null);
        while (res.moveToNext())
        {
            Fontaine fontaine = new Fontaine();
            fontaine.id = res.getString(res.getColumnIndex(Fontaine_COLUMN_ID));
            fontaine.nom = res.getString(res.getColumnIndex(Fontaine_COLUMN_NOM_Fontaine));
            fontaine.libelle = res.getString(res.getColumnIndex(Fontaine_COLUMN_LIBALLE_Fontaine));
            fontaine.statut = res.getString(res.getColumnIndex(Fontaine_COLUMN_STATUT_Fontaine));
            fontaine.image = res.getString(res.getColumnIndex(Fontaine_COLUMN_IMAGE));
            fontaine.localImage = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCAL_IMAGE));
            fontaine.sync = res.getString(res.getColumnIndex(Fontaine_COLUMN_SYNC));
            fontaine.location = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCATION));
            fontaines.add(fontaine);
        }
        return fontaines;
    }
    public static ArrayList<Fontaine> getAllNonSyncedFontaine()
    {
        ArrayList<Fontaine> fontaines = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Fontaine_TABLE_NAME + " where " + Fontaine_COLUMN_SYNC + " = '0';", null);
        while (res.moveToNext())
        {
            Fontaine fontaine = new Fontaine();
            fontaine.id = res.getString(res.getColumnIndex(Fontaine_COLUMN_ID));
            fontaine.nom = res.getString(res.getColumnIndex(Fontaine_COLUMN_NOM_Fontaine));
            fontaine.libelle = res.getString(res.getColumnIndex(Fontaine_COLUMN_LIBALLE_Fontaine));
            fontaine.statut = res.getString(res.getColumnIndex(Fontaine_COLUMN_STATUT_Fontaine));
            fontaine.image = res.getString(res.getColumnIndex(Fontaine_COLUMN_IMAGE));
            fontaine.localImage = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCAL_IMAGE));
            fontaine.sync = res.getString(res.getColumnIndex(Fontaine_COLUMN_SYNC));
            fontaine.location = res.getString(res.getColumnIndex(Fontaine_COLUMN_LOCATION));
            fontaines.add(fontaine);
        }
        return fontaines;
    }
    public static void sync()
    {
        ArrayList<Fontaine> fontaines = getAllNonSyncedFontaine();
        for (Fontaine fontaine : fontaines)
        {
            upload(fontaine);
        }
    }
    private static void upload(final Fontaine fontaine)
    {
        new FilesController(new FilesListener()
        {
            @Override
            public void onFileUploaded(String url, String localPath)
            {
                fontaine.image = url;
                fontaine.localImage = localPath;
                sendFontaine(fontaine);
            }

            @Override
            public void onDownloadFinished()
            {

            }

            @Override
            public void onError(String error)
            {

            }
        }).upload(Uri.parse(fontaine.localImage), fontaine.id);
    }
    private static void sendFontaine(Fontaine fontaine)
    {
        new DataBaseManager(null).addNewFontaine(fontaine);
    }
}

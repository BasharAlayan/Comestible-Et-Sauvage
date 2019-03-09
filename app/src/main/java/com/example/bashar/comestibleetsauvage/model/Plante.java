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

public class Plante
{
	public static final String PLANTE_TABLE_NAME = "plante";
	public static final String PLANTE_COLUMN_ID = "id";
	public static final String PLANTE_COLUMN_NOM_PLANTE = "nom_plante";
	public static final String PLANTE_COLUMN_LIBALLE_PLANTE = "liballe_plante";
	public static final String PLANTE_COLUMN_STATUT_PLANTE = "statut_plante";
	public static final String PLANTE_COLUMN_IMAGE = "image";
	public static final String PLANTE_COLUMN_LOCATION = "location";
	public static final String PLANTE_COLUMN_LOCAL_IMAGE = "local_image";
	public static final String PLANTE_COLUMN_SYNC = "sync";

	public String id;
	public String nom;
	public String libelle;
	public String statut;
	public String image;
	public String location;
	public String localImage;
	public String sync;

	public Plante()
	{

	}

	public Plante(String id, String nom, String libelle, String statut, String image, String location)
	{
		this.id = id;
		this.nom = nom;
		this.libelle = libelle;
		this.statut = statut;
		this.image = image;
		this.location = location;
	}

	public Plante(String id, String nom, String libelle, String statut, String image, String location, String localImage)
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
		contentValues.put(PLANTE_COLUMN_ID, id);
		contentValues.put(PLANTE_COLUMN_NOM_PLANTE, nom);
		contentValues.put(PLANTE_COLUMN_LIBALLE_PLANTE, libelle);
		contentValues.put(PLANTE_COLUMN_STATUT_PLANTE, statut);
		contentValues.put(PLANTE_COLUMN_IMAGE, image);
		contentValues.put(PLANTE_COLUMN_LOCAL_IMAGE, localImage);
		contentValues.put(PLANTE_COLUMN_SYNC, sync);
		contentValues.put(PLANTE_COLUMN_LOCATION, location);
		db.replace(PLANTE_TABLE_NAME, null, contentValues);
	}

	public void insert()
	{
		SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(PLANTE_COLUMN_ID, id);
		contentValues.put(PLANTE_COLUMN_NOM_PLANTE, nom);
		contentValues.put(PLANTE_COLUMN_LIBALLE_PLANTE, libelle);
		contentValues.put(PLANTE_COLUMN_STATUT_PLANTE, statut);
		contentValues.put(PLANTE_COLUMN_IMAGE, image);
		contentValues.put(PLANTE_COLUMN_LOCAL_IMAGE, localImage);
		contentValues.put(PLANTE_COLUMN_SYNC, sync);
		contentValues.put(PLANTE_COLUMN_LOCATION, location);
		db.insert(PLANTE_TABLE_NAME, null, contentValues);
	}

	public void getPlante(String id)
	{
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PLANTE_TABLE_NAME + " where " + PLANTE_COLUMN_ID + "='" + id + "';", null);
		if (res.moveToFirst())
		{
			this.id = id;
			nom = res.getString(res.getColumnIndex(PLANTE_COLUMN_NOM_PLANTE));
			libelle = res.getString(res.getColumnIndex(PLANTE_COLUMN_LIBALLE_PLANTE));
			statut = res.getString(res.getColumnIndex(PLANTE_COLUMN_STATUT_PLANTE));
			image = res.getString(res.getColumnIndex(PLANTE_COLUMN_IMAGE));
			localImage = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCAL_IMAGE));
			sync = res.getString(res.getColumnIndex(PLANTE_COLUMN_SYNC));
			location = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCATION));
		}
	}

	public static ArrayList<Plante> getAllPlante()
	{
		ArrayList<Plante> plantes = new ArrayList<>();
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PLANTE_TABLE_NAME + ";", null);
		while (res.moveToNext())
		{
			Plante plante = new Plante();
			plante.id = res.getString(res.getColumnIndex(PLANTE_COLUMN_ID));
			plante.nom = res.getString(res.getColumnIndex(PLANTE_COLUMN_NOM_PLANTE));
			plante.libelle = res.getString(res.getColumnIndex(PLANTE_COLUMN_LIBALLE_PLANTE));
			plante.statut = res.getString(res.getColumnIndex(PLANTE_COLUMN_STATUT_PLANTE));
			plante.image = res.getString(res.getColumnIndex(PLANTE_COLUMN_IMAGE));
			plante.localImage = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCAL_IMAGE));
			plante.sync = res.getString(res.getColumnIndex(PLANTE_COLUMN_SYNC));
			plante.location = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCATION));
			plantes.add(plante);
		}
		return plantes;
	}

	public static ArrayList<Plante> getAllNonSyncedPlante()
	{
		ArrayList<Plante> plantes = new ArrayList<>();
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PLANTE_TABLE_NAME + " where " + PLANTE_COLUMN_SYNC + " = '0';", null);
		while (res.moveToNext())
		{
			Plante plante = new Plante();
			plante.id = res.getString(res.getColumnIndex(PLANTE_COLUMN_ID));
			plante.nom = res.getString(res.getColumnIndex(PLANTE_COLUMN_NOM_PLANTE));
			plante.libelle = res.getString(res.getColumnIndex(PLANTE_COLUMN_LIBALLE_PLANTE));
			plante.statut = res.getString(res.getColumnIndex(PLANTE_COLUMN_STATUT_PLANTE));
			plante.image = res.getString(res.getColumnIndex(PLANTE_COLUMN_IMAGE));
			plante.localImage = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCAL_IMAGE));
			plante.sync = res.getString(res.getColumnIndex(PLANTE_COLUMN_SYNC));
			plante.location = res.getString(res.getColumnIndex(PLANTE_COLUMN_LOCATION));
			plantes.add(plante);
		}
		return plantes;
	}

	public static void sync()
	{
		ArrayList<Plante> plantes = getAllNonSyncedPlante();
		for (Plante plante : plantes)
		{
			upload(plante);
		}
	}

	private static void upload(final Plante plante)
	{
		new FilesController(new FilesListener()
		{
			@Override
			public void onFileUploaded(String url, String localPath)
			{
				plante.image = url;
				plante.localImage = localPath;
				sendPlante(plante);
			}

			@Override
			public void onDownloadFinished()
			{

			}

			@Override
			public void onError(String error)
			{

			}
		}).upload(Uri.parse(plante.localImage), plante.id);
	}

	private static void sendPlante(Plante plante)
	{
		new DataBaseManager(null).addNewPlant(plante);
	}
}

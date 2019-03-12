package com.example.bashar.comestibleetsauvage;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import com.example.bashar.comestibleetsauvage.firebase.DataBaseManager;
import com.example.bashar.comestibleetsauvage.firebase.DatabaseListener;
import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;

import java.util.ArrayList;

public class App extends Application implements DatabaseListener
{
	public static Context context;
	DataBaseManager dataBaseManager;
	public static boolean isConnected = true;
	public ArrayList<Plante> plants = new ArrayList<>();
	public ArrayList<Fontaine> fontaines = new ArrayList<>();
	@Override
	public void onCreate()
	{
		super.onCreate();
		context = this;
		dataBaseManager = new DataBaseManager(this);
		dataBaseManager.getPlantsData();
		dataBaseManager.getFontainesData();
		networkListener();
	}

	private void networkListener()
	{
		// registering connection listener
		IntentFilter netFilter = new IntentFilter();
		netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(ConnectionChangingListener.getInstance(), netFilter);
	}

	public static void connected()
	{
		isConnected = true;
		//Plante.sync();
		//Fontaine.sync();
	}
	public static void disconnected() {
		isConnected = false;
	}
	@Override
	public void onPlanteRetrieved(ArrayList<Plante> data) {
		//here data will be retrieved
		this.plants = data;
	}

	@Override
	public void onFontaineRetrieved(ArrayList<Fontaine> data) {
		//here data will be retrieved
		this.fontaines = data;
	}

	@Override
	public void onPlanteChanged(ArrayList<Plante> data) {
		// if data updated on firebase
	}

	@Override
	public void onFontaineChanged(ArrayList<Fontaine> data) {
		// if data updated on firebase
	}

	@Override
	public void onError(String error) {

	}
}
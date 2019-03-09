package com.example.bashar.comestibleetsauvage;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import com.example.bashar.comestibleetsauvage.model.Plante;

public class App extends Application
{
	public static Context context;

	@Override
	public void onCreate()
	{
		super.onCreate();
		context = this;

		networkListener();
	}

	private void networkListener()
	{
		// registering connection listener
		IntentFilter netFilter = new IntentFilter();
		netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(ConnectionChangingListener.getInstance(), netFilter);
	}

	public static void sync()
	{
		Plante.sync();
	}
}

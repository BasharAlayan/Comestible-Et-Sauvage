package com.example.bashar.comestibleetsauvage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChangingListener extends BroadcastReceiver
{
	private static final String ACTION_DATA_STATE_CHANGED = "android.intent.action.ANY_DATA_STATE";
	private static ConnectionChangingListener connectionChangingListener;

	public static ConnectionChangingListener getInstance()
	{
		if (connectionChangingListener == null)
			connectionChangingListener = new ConnectionChangingListener();
		return connectionChangingListener;
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			if (intent.getAction().equals(ACTION_DATA_STATE_CHANGED) || intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
			{
				ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
				if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
				{
					App.connected();
				}
				else
				{
					App.disconnected();
				}
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();

		}
	}
}

package com.example.bashar.comestibleetsauvage.firebase;

import java.util.ArrayList;
import com.example.bashar.comestibleetsauvage.Plante;

public interface DatabaseListener
{
	void onDataRetrieved(ArrayList<Plante> data);

	void onDataChanged(ArrayList<Plante> data);

	void onError(String error);
}

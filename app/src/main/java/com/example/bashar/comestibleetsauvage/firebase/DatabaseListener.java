package com.example.bashar.comestibleetsauvage.firebase;

import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;

import java.util.ArrayList;

public interface DatabaseListener
{
	void onPlanteRetrieved(ArrayList<Plante> data);
	void onFontaineRetrieved(ArrayList<Fontaine> data);
	void onPlanteChanged(ArrayList<Plante> data);
	void onFontaineChanged(ArrayList<Fontaine> data);
	void onError(String error);
}

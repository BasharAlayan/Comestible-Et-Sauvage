package com.example.bashar.comestibleetsauvage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bashar.comestibleetsauvage.SQL_local.DBHelper;
import com.example.bashar.comestibleetsauvage.firebase.DataBaseManager;
import com.example.bashar.comestibleetsauvage.firebase.DatabaseListener;
import java.util.ArrayList;

public class Activity_ajouter_plante extends AppCompatActivity
{
	private DBHelper db;
	private String nomP;
	private String libelleP;
	private String statutP;

	private EditText NomP_TF;
	private EditText libelleP_TF;
	private EditText statutP_TF;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajouter_plante);
		db = new DBHelper(this);
		NomP_TF = (EditText) findViewById(R.id.Nom_Plante);
		libelleP_TF = (EditText) findViewById(R.id.Libelle_Plante);
		statutP_TF = (EditText) findViewById(R.id.Statut_Plante);

		Button button = (Button) findViewById(R.id.button_Aj_P);
		button.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				nomP = NomP_TF.getText().toString();
				libelleP = libelleP_TF.getText().toString();
				statutP = statutP_TF.getText().toString();
				if(db.insertPlante(nomP,libelleP,statutP,"image"))
				{
					Toast.makeText(Activity_ajouter_plante.this,"done",Toast.LENGTH_LONG).show();
				}
				if (nomP.equals("") || libelleP.equals("") || statutP.equals(""))
				{
					Toast.makeText(Activity_ajouter_plante.this, "veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(Activity_ajouter_plante.this, "" + nomP + " , " + libelleP + " , " + statutP, Toast.LENGTH_LONG).show();
					ajouterPlante();

				}
			}
		});
	}

	public String getNomPlante()
	{
		return nomP;
	}

	public String getLibellePlante()
	{
		return libelleP;
	}

	public String getStatutPlante()
	{
		return statutP;
	}

	private void ajouterPlante()
	{
		new DataBaseManager(new DatabaseListener()
		{
			@Override
			public void onDataRetrieved(ArrayList<Plante> data)
			{
				Toast.makeText(Activity_ajouter_plante.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onDataChanged(ArrayList<Plante> data)
			{
				Toast.makeText(Activity_ajouter_plante.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(String error)
			{
				Toast.makeText(Activity_ajouter_plante.this, "Error", Toast.LENGTH_LONG).show();
			}
		}).addNewPlant(new Plante(nomP, libelleP, statutP, "Paris", ""));

	}
}

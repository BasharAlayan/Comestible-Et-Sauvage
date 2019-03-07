package com.example.bashar.comestibleetsauvage.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import com.example.bashar.comestibleetsauvage.Plante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.support.annotation.NonNull;

public class DataBaseManager
{
	private DatabaseListener listener;
	private FirebaseFirestore db = FirebaseFirestore.getInstance();

	public DataBaseManager(DatabaseListener listener)
	{
		this.listener = listener;
		listenForPlantsDataChange();
	}

	public void getPlantsData()
	{
		final ArrayList<Plante> dataList = new ArrayList<>();

		// Connecting to firestore and getting the collection (Main Field) data
		db.collection("plant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
		{
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task)
			{
				// on success -> we get the collection documents -> looping on the documents to get each object in it.
				if (task.isSuccessful() && task.getResult() != null)
				{
					for (QueryDocumentSnapshot document : task.getResult())
					{
						Map<String, Object> data = document.getData(); // getting the object as hash map (key, value)

						// creating a new model object and fill it with data and add to our list
						dataList.add(new Plante(String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("location")), String.valueOf(data.get("image"))));
					}
				}
				else
				{
					if (task.getException() != null)
						listener.onError(task.getException().getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
				}
				listener.onDataRetrieved(dataList); // returning the list of data : filled if success or empty if failed
			}
		});
	}

	private void listenForPlantsDataChange()
	{
		// Creating a reference to firestore data base
		final CollectionReference reference = db.collection("plant");

		// creating a listener for any change in the database and get any new added or updated objects
		reference.addSnapshotListener(new EventListener<QuerySnapshot>()
		{
			@Override
			public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e)
			{
				// on any event happened (modify or adding)

				if (e != null) // this is a failure in getting changes
				{
					e.printStackTrace();
					listener.onError(e.getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
					return;
				}

				// on success to get changes -> we get the collection documents -> looping on the documents to get each object in it.
				if (snapshot != null && !snapshot.isEmpty())
				{
					final ArrayList<Plante> dataList = new ArrayList<>();

					for (DocumentSnapshot document : snapshot.getDocuments())
					{
						Map<String, Object> data = document.getData();// getting the object as hash map (key, value)

						if (data != null)
						{
							// creating a new model object and fill it with data and add to our list
							dataList.add(new Plante(String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("location")), String.valueOf(data.get("image"))));
						}
					}
					listener.onDataChanged(dataList); // returning the list of data
				}
			}
		});
	}

	public void addNewPlant(Plante plant)
	{
		// Creating a hash map for creating database record or object
		HashMap<String, Object> data = new HashMap<>();
		data.put("nom", plant.nom);
		data.put("libelle", plant.libelle);
		data.put("statut", plant.statut);
		data.put("location", plant.location);
		data.put("image", plant.image);

		// setting the object on the database
		db.collection("plant").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>()
		{
			@Override
			public void onSuccess(Void aVoid)
			{
				// on success -> nothing will be done -> this is a new data change will come back to you in the method listenForPlantsDataChange() -> process changes normally and return to the view to display
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception e)
			{
				e.printStackTrace();
				listener.onError(e.getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
			}
		});
	}
}

package com.example.bashar.comestibleetsauvage.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;
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
        listenForFontainesDataChange();
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
						Plante plante = new Plante(String.valueOf(data.get("id")), String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("image")), String.valueOf(data.get("location")));
						plante.sync = "1";
						plante.replace();
						dataList.add(plante);
					}
				}
				else
				{
					if (task.getException() != null)
						listener.onError(task.getException().getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
				}
				listener.onPlanteRetrieved(dataList); // returning the list of data : filled if success or empty if failed
			}
		});
	}

    public void getFontainesData()
    {
        final ArrayList<Fontaine> dataList = new ArrayList<>();

        // Connecting to firestore and getting the collection (Main Field) data
        db.collection("fontaine").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
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
                        Fontaine fontaine = new Fontaine(String.valueOf(data.get("id")), String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("image")), String.valueOf(data.get("location")));
                        fontaine.sync = "1";
                        fontaine.replace();
                        dataList.add(fontaine);
                    }
                }
                else
                {
                    if (task.getException() != null)
                        listener.onError(task.getException().getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
                }
                listener.onFontaineRetrieved(dataList); // returning the list of data : filled if success or empty if failed
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
							Plante plante = new Plante(String.valueOf(data.get("id")), String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("image")), String.valueOf(data.get("location")));
							plante.sync = "1";
							plante.replace();
							dataList.add(plante);
						}
					}
					listener.onPlanteChanged(dataList); // returning the list of data
				}
			}
		});
	}
    private void listenForFontainesDataChange()
    {
        // Creating a reference to firestore data base
        final CollectionReference reference = db.collection("fontaine");

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
                    final ArrayList<Fontaine> dataList = new ArrayList<>();

                    for (DocumentSnapshot document : snapshot.getDocuments())
                    {
                        Map<String, Object> data = document.getData();// getting the object as hash map (key, value)

                        if (data != null)
                        {
                            // creating a new model object and fill it with data and add to our list
                            Fontaine fontaine = new Fontaine(String.valueOf(data.get("id")), String.valueOf(data.get("nom")), String.valueOf(data.get("libelle")), String.valueOf(data.get("statut")), String.valueOf(data.get("image")), String.valueOf(data.get("location")));
                            fontaine.sync = "1";
                            fontaine.replace();
                            dataList.add(fontaine);
                        }
                    }
                    listener.onFontaineChanged(dataList); // returning the list of data
                }
            }
        });
    }

	public void addNewPlant(final Plante plant)
	{
		// Creating a hash map for creating database record or object
		HashMap<String, Object> data = new HashMap<>();
		data.put("id", plant.id);
		data.put("nom", plant.nom);
		data.put("libelle", plant.libelle);
		data.put("statut", plant.statut);
		data.put("image", plant.image);
        data.put("locaion", plant.location);

		// setting the object on the database
		db.collection("plant").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>()
		{
			@Override
			public void onSuccess(Void aVoid)
			{
				// on success -> nothing will be done only insert to database locally -> this is a new data change will come back to you in the method listenForPlantsDataChange() -> process changes normally and
				// return to the view to display
				plant.sync = "1"; // inserted to remote database
				plant.insert();
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception e)
			{
				plant.sync = "0"; // not inserted to remote database
				plant.insert();
				e.printStackTrace();
				listener.onError(e.getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
			}
		});
	}
	public void addNewFontaine(final Fontaine fontaine)
	{
		// Creating a hash map for creating database record or object
		HashMap<String, Object> data = new HashMap<>();
		data.put("id", fontaine.id);
		data.put("nom", fontaine.nom);
		data.put("libelle", fontaine.libelle);
		data.put("statut", fontaine.statut);
		data.put("image", fontaine.image);
        data.put("location", fontaine.location);

		// setting the object on the database
		db.collection("fontaine").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>()
		{
			@Override
			public void onSuccess(Void aVoid)
			{
				// on success -> nothing will be done only insert to database locally -> this is a new data change will come back to you in the method listenForPlantsDataChange() -> process changes normally and
				// return to the view to display
				fontaine.sync = "1"; // inserted to remote database
				fontaine.insert();
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception e)
			{
				fontaine.sync = "0"; // not inserted to remote database
				fontaine.insert();
				e.printStackTrace();
				listener.onError(e.getMessage()); // on failure -> tell the waiting view that getting process failed and return the failure message
			}
		});
	}
}

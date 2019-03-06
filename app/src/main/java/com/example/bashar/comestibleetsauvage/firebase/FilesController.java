package com.example.bashar.comestibleetsauvage.firebase;

import java.io.File;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.net.Uri;
import android.support.annotation.NonNull;

public class FilesController
{
	private FilesListener listener; // View waiting for the responses
	private FirebaseStorage storage = FirebaseStorage.getInstance("gs://comestible-et-sauvage.appspot.com"); // Firebase Storage reference

	public FilesController(FilesListener listener)
	{
		this.listener = listener;
	}

	public void upload(final Uri file, final String name)
	{
		// Create a storage reference from our app
		StorageReference storageRef = storage.getReference();

		// Create reference for the file by its name
		final StorageReference reference = storageRef.child("images/IMG_" + name + ".jpg");

		// Starting upload
		UploadTask uploadTask = reference.putFile(file);

		// Register observers to listen for when the upload is done or if it fails
		uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
		{
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
			{
				reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
				{
					@Override
					public void onSuccess(Uri uri)
					{
						listener.onFileUploaded(uri.toString()); // on success -> tell the waiting view that upload finished and return the url of the file
					}
				}).addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						e.printStackTrace();
						listener.onError(e.getMessage()); // on failure -> tell the waiting view that upload failed and return the failure message
					}
				});
			}
		}).addOnFailureListener(new OnFailureListener()
		{
			@Override
			public void onFailure(@NonNull Exception e)
			{
				e.printStackTrace();
				listener.onError(e.getMessage()); // on failure -> tell the waiting view that upload failed and return the failure message
			}
		});
	}

	public void download(String fileName, String filePath)
	{
		try
		{
			// Create a storage reference from our app
			StorageReference storageRef = storage.getReference();

			// Create reference for the file by its name
			StorageReference reference = storageRef.child("images/IMG_" + fileName + ".jpg");

			// Create the local file to download in it
			final File localFile = new File(filePath);
			localFile.createNewFile();

			// Register observers to listen for when the download is done or if it fails
			reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
			{
				@Override
				public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
				{
					listener.onDownloadFinished(); // on success -> tell the waiting view that upload finished
				}
			}).addOnFailureListener(new OnFailureListener()
			{
				@Override
				public void onFailure(@NonNull Exception e)
				{
					e.printStackTrace();
					listener.onError(e.getMessage()); // on failure -> tell the waiting view that upload failed and return the failure message
				}
			});
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			listener.onError(ex.getMessage()); // on failure -> tell the waiting view that upload failed and return the failure message
		}
	}
}

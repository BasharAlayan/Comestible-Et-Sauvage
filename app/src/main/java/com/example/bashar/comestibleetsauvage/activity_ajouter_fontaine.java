package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bashar.comestibleetsauvage.firebase.DataBaseManager;
import com.example.bashar.comestibleetsauvage.firebase.DatabaseListener;
import com.example.bashar.comestibleetsauvage.firebase.FilesController;
import com.example.bashar.comestibleetsauvage.firebase.FilesListener;
import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class activity_ajouter_fontaine extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private String nomF;
    private String libelleF;
    private String statutF;
    private Uri imagePath;
    private EditText NomF_TF;
    private EditText libelleF_TF;
    private EditText statutF_TF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_fontaine);

            NomF_TF=(EditText)findViewById(R.id.Nom_Fontaine);
            libelleF_TF=(EditText)findViewById(R.id.Libelle_Fontaine);
            statutF_TF=(EditText)findViewById(R.id.StatutFontaine);
            Button imgBtn = findViewById(R.id.imgBtn);
            Button button = (Button)findViewById(R.id.button_Aj_F);
        imgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity_ajouter_fontainePermissionsDispatcher.openGalleryWithCheck(activity_ajouter_fontaine.this);
            }
        });
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    nomF= NomF_TF.getText().toString();
                    libelleF= libelleF_TF.getText().toString();
                    statutF= statutF_TF.getText().toString();
                    if(nomF.equals("") || libelleF.equals("") || statutF.equals("")){
                        Toast.makeText(activity_ajouter_fontaine.this, "veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(activity_ajouter_fontaine.this,""+nomF+" , "+libelleF+" , "+statutF ,Toast.LENGTH_LONG).show();
                    }
                    ajouterFontaine();
                }
            });
        }
    private void sendFontaine(String id, String url, String localPath)
    {
        new DataBaseManager(new DatabaseListener()
        {
            @Override
            public void onPlanteRetrieved(ArrayList<Plante> data) {

            }

            @Override
            public void onFontaineRetrieved(ArrayList<Fontaine> data)
            {
                Toast.makeText(activity_ajouter_fontaine.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlanteChanged(ArrayList<Plante> data) {

            }

            @Override
            public void onFontaineChanged(ArrayList<Fontaine> data)
            {
                Toast.makeText(activity_ajouter_fontaine.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error)
            {
                Toast.makeText(activity_ajouter_fontaine.this, "Error", Toast.LENGTH_LONG).show();
            }
        }).addNewFontaine(new Fontaine(id, nomF, libelleF, statutF, url,"paris F", localPath));
    }
    private void upload(final String id)
    {
        new FilesController(new FilesListener()
        {
            @Override
            public void onFileUploaded(String url, String localPath)
            {
                sendFontaine(id, url, localPath);
            }

            @Override
            public void onDownloadFinished()
            {

            }

            @Override
            public void onError(String error)
            {
                sendFontaine(id, "", imagePath.getPath());
            }
        }).upload(imagePath, id);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null)
            {
                Uri imageUri = data.getData();

                if (imageUri != null)
                {
                    imagePath = imageUri;
                }
                else
                {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void ajouterFontaine()
    {
        final String id = String.valueOf(new Date().getTime());
        upload(id);
    }
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        activity_ajouter_fontainePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    public String getRealPathFromURI(Uri contentUri)
    {
        // can post image
        if (contentUri != null)
        {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                cursor.close();
                return path;
            }
        }
        return "";
    }

    public String getUriFromExtra(Bitmap image)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return getRealPathFromURI(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), image, "Title", null)));
    }
}


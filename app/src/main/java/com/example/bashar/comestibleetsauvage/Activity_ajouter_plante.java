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
public class Activity_ajouter_plante extends AppCompatActivity {
    private String nomP;
    private String libelleP;
    private String statutP;
    private Uri imagePath;
    private EditText NomP_TF;
    private EditText libelleP_TF;
    private EditText statutP_TF;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plante);

        NomP_TF = findViewById(R.id.Nom_Plante);
        libelleP_TF = findViewById(R.id.Libelle_Plante);
        statutP_TF = findViewById(R.id.Statut_Plante);
        Button button = findViewById(R.id.button_Aj_P);
        Button imgBtn = findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_ajouter_plantePermissionsDispatcher.openGalleryWithCheck(Activity_ajouter_plante.this);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nomP = NomP_TF.getText().toString();
                libelleP = libelleP_TF.getText().toString();
                statutP = statutP_TF.getText().toString();
                Toast.makeText(Activity_ajouter_plante.this, "" + nomP + " , " + libelleP + " , " + statutP, Toast.LENGTH_LONG).show();
                ajouterPlante();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                // Uri imageUri = data.getData();

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                if (photo != null) {
                    imagePath = Uri.parse("file://" + getUriFromExtra(photo));
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ajouterPlante() {
        final String id = String.valueOf(new Date().getTime());
        if (App.isConnected)
            upload(id);
        else
            storeLocally(id);
    }

    private void storeLocally(String id) {
        new Plante(id, nomP, libelleP, statutP, imagePath.getPath(), "paris P", imagePath.getPath()).insert();
    }

    private void upload(final String id) {
        new FilesController(new FilesListener() {
            @Override
            public void onFileUploaded(String url, String localPath) {
                sendPlante(id, url, localPath);
            }

            @Override
            public void onDownloadFinished() {

            }

            @Override
            public void onError(String error) {
                storeLocally(id);
            }
        }).upload(imagePath, id);
    }

    private void sendPlante(String id, String url, String localPath) {
        new DataBaseManager(new DatabaseListener() {
            @Override
            public void onPlanteRetrieved(ArrayList<Plante> data) {
                Toast.makeText(Activity_ajouter_plante.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFontaineRetrieved(ArrayList<Fontaine> data) {

            }

            @Override
            public void onPlanteChanged(ArrayList<Plante> data) {
                Toast.makeText(Activity_ajouter_plante.this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFontaineChanged(ArrayList<Fontaine> data) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(Activity_ajouter_plante.this, "Error", Toast.LENGTH_LONG).show();
            }
        }).addNewPlant(new Plante(id, nomP, libelleP, statutP, url, "paris P", localPath));
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void openGallery() {
        // Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Activity_ajouter_plantePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public String getRealPathFromURI(Uri contentUri) {
        // can post image
        if (contentUri != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);
            if (cursor != null) {
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

    public String getUriFromExtra(Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return getRealPathFromURI(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), image, "Title", null)));
    }
}

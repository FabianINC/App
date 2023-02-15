package com.archivo.MainMenu.menu_fragments.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.archivo.app.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddSpace extends AppCompatActivity {

    private EditText txtAddLocation, txtAddPrice, txtAddDescription;
    private Button btnAddPlace, btnSelectImg;
    private FirebaseFirestore firestore;
    private StorageReference storageRef;
    private ImageView addImg;
    private static final int GALLERY_INTENT = 1;
    //Guarda datos de la imagen
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);

        //Invisibiliza la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Inicializo la base de datos y su storage
        firestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        addImg = findViewById(R.id.addImg);
        txtAddLocation = findViewById(R.id.txtAddLocation);
        txtAddDescription = findViewById(R.id.txtAddDescription);
        txtAddPrice = findViewById(R.id.txtAddPrice);
        btnAddPlace = findViewById(R.id.btnSendPlace);



        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String location = txtAddLocation.getText().toString();
                //El .trim() elimina los espacions y unicamente deja el texto
                String price = txtAddPrice.getText().toString().trim();
                String description = txtAddDescription.getText().toString();


                if( location.isEmpty() || price.isEmpty() || description.isEmpty() ){

                    Toast.makeText(AddSpace.this, "Algunos campos no se han completado", Toast.LENGTH_SHORT).show();


                }else{

                      uploadPicture();
                      postPlace(location, price, description);

                }

            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                choosePicture();

            }
        });


    }

    private void choosePicture() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_INTENT);


    }

    private void postPlace(String location, String price, String description) {
        Map<String, Object> map = new HashMap<>();
                map.put("location", location);
                map.put("price", price);
                map.put("description", description);



            firestore.collection("Places").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Toast.makeText(AddSpace.this, "Creado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    Toast.makeText(AddSpace.this, "Error al ingresar", Toast.LENGTH_SHORT).show();

                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();
            addImg.setImageURI(imageUri);

        }
    }

    private void uploadPicture() {

        StorageReference filepath = storageRef.child("pictures").child(imageUri.getLastPathSegment());

        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


            }
        });

    }




    }
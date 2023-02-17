package com.archivo.MainMenu.Login_RegisterActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    private EditText inputUserName,inputEmail,inputPassword,inputPasswordConfirmation;

    private Button btnRegister;

    private TextView existingUser;

    private ProgressDialog registerProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ///Invisibiliza la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // SE IDENTIFICA CADA VARIABLE JAVA CON LA UI
        inputUserName = findViewById(R.id.txtAddLocation);
        inputEmail = findViewById(R.id.txtNewEmail);
        inputPassword = findViewById(R.id.txtRegisterPassword);
        inputPasswordConfirmation = findViewById(R.id.txtPasswordConfirmation);

        btnRegister = findViewById(R.id.btnAddPlace);

        existingUser = findViewById(R.id.txtExistingAccount);

        //LISTENER PARA REGISTRAR UN NUEVO USUARIO MEDIANTE CORREO - CONTRASEÑA
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnRegisterClicked) {
                performEmailRegister();
            }
        });

        //LISTENER PARA VOLVER A LA PANTALLA DE LOGIN
        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View alredyRegisteredClicked) {
                alreadyRegistered();
            }
        });




    }


    private FirebaseFirestore registerAuth;

    /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private void performEmailRegister(){

        //SE CREA UN NUEVO PROGRESS DIALOG
        registerProgress = new ProgressDialog(RegisterActivity.this);

        // SE OBTIENEN TODOS LOS DATOS INGRESADOS
        final String userName = inputUserName.getText().toString();
        final String userEmail = inputEmail.getText().toString();
        final String userPassword = inputPassword.getText().toString();
        final String userPasswordConfirmation = inputPasswordConfirmation.getText().toString();

        // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
        if( !userEmail.matches(emailPattern) ){
            inputEmail.setError( getString(R.string.txtWrongEmailInput) );
            inputEmail.setText("");

        // VERIFICAMOS SI LA CONTRASEÑA ESTÁ VACÍA O ES MUY CORTA
        }else if( userPassword.isEmpty() || userPassword.length() < 6 ){
            inputPassword.setError( getString(R.string.txtWrongPasswordInput) );
            inputPassword.setText("");
            inputPasswordConfirmation.setText("");


        // VERIFICAMOS QUE AMBAS CONTRASEÑAN COINCIDAN
        }else if( !userPassword.equals(userPasswordConfirmation) ){
            inputPasswordConfirmation.setError( getString(R.string.txtWrongConfirmationInput) );
            inputPassword.setText("");
            inputPasswordConfirmation.setText("");


        // SI TODAS LAS VERIFICACIONES SON CORRECTAS
        }else{
            registerProgress.setMessage( getString(R.string.txtProgressDialogMessage) );
            registerProgress.setTitle( getString(R.string.txtProgressDialogTitle) );
            registerProgress.setCanceledOnTouchOutside(false);
            registerProgress.show();

            // SE REALIZA LA PETICIÓN PARA REGISTRAR UN USUARIO
            registerAuth = FirebaseFirestore.getInstance();
            addNewUser(userName, userEmail, userPassword);
        }

    }

    // MÉTODO PARA AÑADIR UN NUEVO USUARIO A FIRESTORE
    private void addNewUser(String userName, String userEmail, String userPassword){

        // SE AGREGAN LOS DATOS A LA PETICIÓN
        Map<String, Object> userData = new HashMap<>();
        userData.put("Nombre", userName);
        userData.put("Email", userEmail);
        userData.put("Contraseña", userPassword);

        registerAuth.collection("Usuarios").add(userData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                showSuccessfulToast();

                Intent loginScreen = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginScreen);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showUnsuccessfulToast();
            }
        });

    }

    // MÉTODO PARA PASAR A LA PANTALLA DE LOGIN
    public void alreadyRegistered(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE EFECTIVO
    public void showSuccessfulToast(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_success,(ViewGroup) findViewById(R.id.check_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.txt_toast);

        txtMessage.setText(R.string.toastSuccess); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE ERRONEO
    public void showUnsuccessfulToast(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_unsuccess,(ViewGroup) findViewById(R.id.toastUnsuccess));

        TextView txtMessage = toastRegistration.findViewById(R.id.txtUnsuccessfulMessage);

        txtMessage.setText(R.string.toastUnsuccess); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }


}
package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.archivo.Animation.progressDialog;
import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
import com.archivo.MainMenu.MainActivity;
import com.archivo.MainMenu.menu_fragments.profile.Profile;
import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailLogin extends LoginActivity {

    /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_email_login);



        // SE OBTIENE EL CORREO - CONTRASEÑA
       String userEmail = getIntent().getStringExtra("userEmail");
       String userPassword = getIntent().getStringExtra("userPassword");

        // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
        if( !userEmail.matches(emailPattern) ){
            inputEmail.setError( getString(R.string.txtWrongEmailInput));
            inputEmail.setText("");

            // VERIFICAMOS SI LA CONTRASEÑA ESTÁ VACÍA O ES MUY CORTA
        }else if( userPassword.isEmpty() || userPassword.length() < 6 ){
            inputPassword.setError( getString(R.string.txtWrongPasswordInput) );
            inputPassword.setText("");

            // SI TODAS LAS VERIFICACIONES SON CORRECTAS
        }else{

            // SE CREA EL PROGRESS DIALOG
            progressDialog loadingProgress = new progressDialog(EmailLogin.this);
            loadingProgress.show();

            FirebaseAuth loginAuth = FirebaseAuth.getInstance();
            FirebaseUser loginUser = loginAuth.getCurrentUser();



            // SE REALIZA LA PETICIÓN PARA LOGEAR UN USUARIO
            loginAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful() ){
                        loadingProgress.dismiss(); // SE QUITA EL PROGRESS DIALOG

                        try{
                            Profile setParameters = new Profile();
                            setParameters.setFireBaseAuth(loginAuth);
                            setParameters.setFireBaseUser(loginUser);

                            showSuccessfulToast();

                            // SE LANZA LA NUEVA ACTIVIDAD
                            Intent mainScreen = new Intent(EmailLogin.this, MainActivity.class);
                            mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(mainScreen);

                        }catch(Exception error){
                            Toast.makeText(EmailLogin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        loadingProgress.dismiss(); // SE QUITA EL PROGRESS DIALOG
                        showUnsuccessfulToast();
                    }

                }
            });
        }

    }

}

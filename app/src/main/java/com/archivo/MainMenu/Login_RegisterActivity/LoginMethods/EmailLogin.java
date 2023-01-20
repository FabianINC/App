package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
import com.archivo.MainMenu.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailLogin extends AppCompatActivity {

    // VARIABLES GLOBALES

    public void performEmailLogin(EditText inputEmail, EditText inputPassword, LoginActivity loginActivity){

        /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        final String userEmail = inputEmail.getText().toString();
        final String userPassword= inputPassword.getText().toString();

        // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
        if( !userEmail.matches(emailPattern) ){
            inputEmail.setError("Ingrese un correo eléctronico válido");
            inputEmail.setText("");

            // VERIFICAMOS SI LA CONTRASEÑA ESTÁ VACÍA O ES MUY CORTA
        }else if( userPassword.isEmpty() || userPassword.length() < 6 ){
            inputPassword.setError("Ingrese una contraseña válido");
            inputPassword.setText("");

            // SI TODAS LAS VERIFICACIONES SON CORRECTAS
        }else{

            FirebaseAuth loginAuth = FirebaseAuth.getInstance();;
            FirebaseUser loginUser = loginAuth.getCurrentUser();;

            ProgressDialog loginProgress = new ProgressDialog(loginActivity);
            loginProgress.setMessage("Por favor espere...");
            loginProgress.setTitle("Verificando credenciles");
            loginProgress.setCanceledOnTouchOutside(false);
            loginProgress.show();

            // SE REALIZA LA PETICIÓN PARA LOGEAR UN USUARIO
            loginAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful() ){
                        loginProgress.dismiss();
                        //showSuccessfulToast("¡Bienvenido!");

                        Intent mainScreen = new Intent(loginActivity, MainActivity.class);
                        mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainScreen);

                    }else{
                        loginProgress.dismiss();
                        //showUnsuccessfulToast(task.getException().getMessage());
                    }

                }
            });
        }
    }

}

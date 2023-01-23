package com.archivo.MainMenu.Login_RegisterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.MainMenu.MainActivity;
import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPasswordActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    private Button btnResetPassword;
    private EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        btnResetPassword = findViewById(R.id.btnResetPassword);
        inputEmail = findViewById(R.id.txtEmail);

        /* SE CREAN LOS LISTENERS DE CADA BOTÓN */

        // LISTENER PARA REESTABLECER LA CONTRASEÑA
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnResetPasswordClicked) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        final String userEmail = inputEmail.getText().toString();

        /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
        if( !userEmail.matches(emailPattern) ){
            //inputEmail.setError(R.string.txtWrongEmailInput);
            inputEmail.setText("");

        }else {
            FirebaseAuth resetPasswordAuth = FirebaseAuth.getInstance();

            // SE ENVÍA UN CORREO DE REESTABLECIMIENTO
            resetPasswordAuth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, R.string.toastEmailSend, Toast.LENGTH_SHORT).show();

                                // SE LANZA LA NUEVA ACTIVIDAD
                                Intent loginScreen = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(loginScreen);


                            }else{
                                Toast.makeText(ResetPasswordActivity.this, R.string.toastErrorOnReset, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}


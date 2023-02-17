package com.archivo.MainMenu.Login_RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.Animation.progressDialog;
import com.archivo.MainMenu.Login_RegisterActivity.LoginMethods.EmailLogin;
import com.archivo.MainMenu.Login_RegisterActivity.LoginMethods.FacebookLogin;
import com.archivo.MainMenu.Login_RegisterActivity.LoginMethods.GoogleLogin;
import com.archivo.MainMenu.MainActivity;
import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    // VARIABLES GLOBALES
    private EditText inputEmail,inputPassword;
    private TextView newUser, resetPassword;

    private ImageView guestUser;
    private Button btnLogin;

    private ImageView btnGoogleLogin, btnFacebookLogin, btnPhoneLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Invisibiliza la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        inputEmail = findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPassword);

        newUser = findViewById(R.id.txtNewAccount);
        guestUser = findViewById(R.id.imgGuestLogin);
        resetPassword = findViewById(R.id.txtForgotPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnPhoneLogin = findViewById(R.id.btnPhoneLogin);

        /* SE CREAN LOS LISTENERS DE CADA BOTÓN */
        
        // LISTENER PARA INICIO DE SESIÓN MEDIANTE CORREO - CONTRASEÑA
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnLoginClicked) {
                performEmailLogin();
            }
        });
        
        // LISTENER PARA INICIO DE SESIÓN MEDIANTE GOOGLE
        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnGoogleLoginClicked) {
                performGoogleLogin();
            }
        });
        
        // LISTENER PARA INICIO DE SESIÓN MEDIANTE FACEBOOK
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnFacebookLoginClicked) {
                performFacebookLogin();
            }
        });
        
        // LISTENER PARA INICIO DE SESIÓN MEDIANTE NÚMERO TELEFONICO
        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnPhoneNumberClicked) {
                performPhoneLogin();
            }
        });

        // LISTENER PARA REGISTRAR UN USUARIO
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View newUserCliked) {
                newUserScreen();
            }
        });

        // LISTENER PARA USAR LA APP COMO INVITADO
        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View guestUserClicked) {
                guestLogin();
            }
        });

        // LISTENER PARA REESTABLECER LA CONTRASEÑA
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View forgotPasswordClicked) {
                resetPassword();
            }
        });

    }


/* ------------------------------------ CORREO - CONTRASEÑA ------------------------------------ */
    private void performEmailLogin(){
        final String userEmail = inputEmail.getText().toString();
        final String userPassword = inputPassword.getText().toString();

        Intent emailLogin = new Intent(LoginActivity.this, EmailLogin.class);
        emailLogin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        emailLogin.putExtra("userEmail", userEmail); //SE ENVIA EL CORREO
        emailLogin.putExtra("userPassword", userPassword); // SE ENVIA LA CONTRASEÑA

        startActivity(emailLogin);
        overridePendingTransition(0,0); // SE ELIMINA LA ANIMACIÓN DEL CAMBIO DE ACTIVITY
    }
/* --------------------------------------------------------------------------------------------- */



/* ------------------------------------------- GOOGLE ------------------------------------------ */
    private void performGoogleLogin(){
        Intent googleLogin = new Intent(LoginActivity.this, GoogleLogin.class);
        googleLogin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(googleLogin);
        overridePendingTransition(0,0); // SE ELIMINA LA ANIMACIÓN DEL CAMBIO DE ACTIVITY
    }
/* --------------------------------------------------------------------------------------------- */


/* ------------------------------------------ FACEBOOK ----------------------------------------- */
    private void performFacebookLogin(){
        Intent facebookLogin = new Intent(LoginActivity.this, FacebookLogin.class);
        startActivity(facebookLogin);
    }
/* --------------------------------------------------------------------------------------------- */



/* ------------------------------------- NÚMERO DE TELEFONO ------------------------------------ */
    // MÉTODO PARA INICIAR SESIÓN CON FACEBOOK
    private void performPhoneLogin(){
        Intent phoneLoginScreen = new Intent(LoginActivity.this, PhoneLoginActivity.class);
        phoneLoginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(phoneLoginScreen);
    }
/* --------------------------------------------------------------------------------------------- */



    // MÉTODO PARA MOSTRAR LA PANTALLA DE REGISTRO
    private void newUserScreen(){
        Intent registerScreen = new Intent(LoginActivity.this,RegisterActivity.class);

        // LLAMADO A LA PANTALLA DE REGISTRO
        startActivity(registerScreen);
    }

    // MÉTODO PARA USAR LA APP COMO 'Invitado'
    private void guestLogin(){
        Intent mainScreen = new Intent(LoginActivity.this, MainActivity.class);

        //LLAMADO A LA PANTALLA PRINCIPAL
        startActivity(mainScreen);
    }

    // MÉTODO PARA REESTABLECER LA CONTRASEÑA
    private void resetPassword(){
        Intent resetScreen = new Intent(LoginActivity.this, ResetPasswordActivity.class);

        // LLAMADO A LA PANTALLA DE REESTABLECIMIENTO DE CONTRASEÑA
        startActivity(resetScreen);
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

    // METODO PARA MOSTRAR UN TOAST QUE FUE ERRONEO
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
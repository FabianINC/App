package com.archivo.MainMenu.Login_RegisterActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.MainMenu.MainActivity;

import com.archivo.app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



public class LoginActivity extends AppCompatActivity {


    // VARIABLES GLOBALES
    EditText inputEmail,inputPassword;
    TextView newUser, resetPassword;

    ImageView guestUser;
    Button btnLogin;

    ImageView btnGoogleLogin, btnFacebookLogin, btnPhoneLogin;

    ProgressDialog loginProgress;

    FirebaseAuth loginAuth;
    FirebaseUser loginUser;

    //String  email , password, name , apiKey;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        //SE CREA UN NUEVO PROGRESS DIALOG
        loginProgress = new ProgressDialog(LoginActivity.this);

        loginAuth = FirebaseAuth.getInstance();
        loginUser = loginAuth.getCurrentUser();

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
                phoneNumberLoginScreen();
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





        /*
        sharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);

        // SE MANTIENE LA SESION INICIADA UNA VEZ QUE HAYA INICIADO SESION
        if(sharedPreferences.getString("logged", "false").equals("true")){
            startActivity(new Intent(LoginActivity.this, Main.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SE CONVIERTE A STRING LOS VALORES INGRESADOS
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    //Se utiliza un jsonObject ya que el php esta en formato JSON
                                    JSONObject jsonObject = new JSONObject(response);
                                    //Esto retorna el estado del php, si el inicio de sesion fue exitoso o no
                                    String status = jsonObject.getString("status");
                                    //Este retonrna el mensaje, utilizado sobretodo en caso de errores
                                    String message = jsonObject.getString("message");


                                    if(status.equals("success")){

                                        //En caso de que se inicie sesion exitosamente se recuperan los datos del php
                                       name = jsonObject.getString("name");
                                       email = jsonObject.getString("email");
                                       apiKey = jsonObject.getString("apiKey");

                                       Estos datos son utilizados en SharedPreferences lo que despues ayudara a
                                         mantener la sesion iniciada despues de la primera vez que inicio sesion
                                       SharedPreferences.Editor editor = sharedPreferences.edit();
                                       editor.putString("logged" , "true");
                                       editor.putString("name" , name);
                                       editor.putString("email" , email);
                                       editor.putString("apiKey" , apiKey);
                                       editor.apply();
                                       showSuccesfulToast("Login successful");
                                       startActivity(new Intent(LoginActivity.this, Main.class));
                                       finish();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override

                     SE MUESTRA UN TOAST CON EL ERROR
                    public void onErrorResponse(VolleyError error) {
                        showUnsuccessfulToast(error.getMessage());
                    }
                }){

                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    */
    }

    /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // MÉTODO PARA INICIAR SESIÓN CON CONRREO Y CONTRASEÑA
    private void performEmailLogin(){

        // SE OBTIENEN TODOS LOS DATOS INGRESADOS
        final String userEmail = inputEmail.getText().toString();
        final String userPassword = inputPassword.getText().toString();

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
                        showSuccessfulToast("¡Bienvenido!");

                        Intent mainScreen = new Intent(LoginActivity.this, MainActivity.class);
                        mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainScreen);

                    }else{
                        loginProgress.dismiss();
                        showUnsuccessfulToast(task.getException().getMessage());
                    }

                }
            });

        }
    }

    // MÉTODO PARA INICIAR SESIÓN CON GOOGLE
    private static final int RC_SIGN_IN = 101;
    private void performGoogleLogin(){

        //CONFIGURANDO EL SERVICIO DE GOOGLE
        GoogleSignInOptions googleSignIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string
                        .default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignIn);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent){
        super.onActivityResult(requestCode, resultCode, dataIntent);

        //SE VERIFICA EL RESULTADO DEVUELTO DEL INTENT DEL LOGIN MEDIANTE GOOGLE
        if( requestCode == RC_SIGN_IN ){
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(dataIntent);

            try{
                GoogleSignInAccount userAccount = signInTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(userAccount.getIdToken());
            }catch(ApiException apiError){
                showUnsuccessfulToast("Ha ocurrido un problema a la hora de conectarse");
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential userCredential = GoogleAuthProvider.getCredential(idToken, null);

        loginAuth.signInWithCredential(userCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser signedUser = loginAuth.getCurrentUser();

                            showSuccessfulToast("¡Bienvenido!");

                            Intent mainScreen = new Intent(LoginActivity.this, MainActivity.class);
                            mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainScreen);

                        }else{
                            showUnsuccessfulToast("No se ha podido iniciar sesión");
                        }
                    }
                });
    }

    // MÉTODO PARA INICIAR SESIÓN CON FACEBOOK
    private void performFacebookLogin(){

    }

    // MÉTODO PARA MOSTRAR LA PANTALLA DE LOGIN CON NÚMERO TELEFONICO
    public void phoneNumberLoginScreen (){
        Intent phoneLoginScreen = new Intent(LoginActivity.this, PhoneLoginActivity.class);

        // LLAMADO A LA PANTALLA DE LOGIN CON NÚMERO DE TELEFONO
        startActivity(phoneLoginScreen);
    }

    
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
    public void showSuccessfulToast(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_check,(ViewGroup) findViewById(R.id.check_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.txt_toast);

        txtMessage.setText(toastMessage); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }

    // METODO PARA MOSTRAR UN TOAST QUE FUE ERRONEO
    public void showUnsuccessfulToast(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_wrong,(ViewGroup) findViewById(R.id.wrong_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.toast_wrong);

        txtMessage.setText(toastMessage); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }


}
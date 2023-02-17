package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
import com.archivo.MainMenu.Login_RegisterActivity.RegisterActivity;
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

public class RESPALDO {
/*
    private FirebaseFirestore emailAuth;

    public static void main(){
        final String userName = "Andrés Víquez";
        final String userEmail = "viquezandres1811@gmail.com";
        final String userPassword = "12345678";

        // SE REALIZAN TODAS LAS VERIFICACIONES


        //SE HACE LA CONSULTA
        emailAuth = FirebaseFirestore.getInstance(); // SE APUNTA A LA BASE DE DATOS

        //SE REGISTRA
        postUser(userName, userEmail, userPassword);

    }



    private void postUser( String userName, String userEmail, String userPassword){
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", userName);
        map.put("email", userEmail);
        map.put("contraseña", userPassword);

        emailAuth.collection("Usuarios").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //MENSAJE

                // PANTALLA DE LOGIN
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // MENSAJE
            }
        });
    }

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





    public class RegisterActivity extends AppCompatActivity {

        // VARIABLES GLOBALES
        private EditText inputUserName, inputEmail, inputPassword, inputPasswordConfirmation;

        private Button btnRegister;

        private TextView existingUser;
        String name, email, password, passwordConfirmation;

        private ProgressDialog registerProgress;


        private FirebaseAuth registerAuth;
        private FirebaseUser registerUser;

        //private static final String URL1 = "http://152.231.173.118/usuarios/save.php";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            ///Invisibiliza la status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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

            //SE CREA UN NUEVO PROGRESS DIALOG
            registerProgress = new ProgressDialog(com.archivo.MainMenu.Login_RegisterActivity.RegisterActivity.this);

            registerAuth = FirebaseAuth.getInstance();
            registerUser = registerAuth.getCurrentUser();

        // EVENTO QUE SE EJECUTA CUANDO SE DA CLICK EN btnRegister
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SE CONVIERTE A STRING CADA VALOR INGRESADO
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();
                passwordConfirmation = txt_confirmPassword.getText().toString();

                if(!password.equals(passwordConfirmation)){

                    showUnsuccessfulToast("La contraseña y su confirmación deben coincidir");

                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){ // SI DEVUELVE 'success' ES PORQUE SE PUDO REGISTRAR EN LA BASE DE DATOS
                                    showSuccessfulToast("Registro exitoso");

                                    // LLAMADO A LA PANTALLA DE LOGIN
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else{
                                    // DEVUELVE EL ERROR
                                    showUnsuccessfulToast(response);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        // SE INGRESAN LOS DATOS DE CADA PARAMETRO DEL QUERY PHP
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                // SE AÑADE EL QUERY DE REGISTRO
                queue.add(stringRequest);

            }
        });


        }

         VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // MÉTODO PARA REALIZAR EL REGISTRO
        private void performEmailRegister() {

            // SE OBTIENEN TODOS LOS DATOS INGRESADOS
            final String userName = inputUserName.getText().toString();
            final String userEmail = inputEmail.getText().toString();
            final String userPassword = inputPassword.getText().toString();
            final String userPasswordConfirmation = inputPasswordConfirmation.getText().toString();

            // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
            if (!userEmail.matches(emailPattern)) {
                inputEmail.setError(getString(R.string.txtWrongEmailInput));
                inputEmail.setText("");

                // VERIFICAMOS SI LA CONTRASEÑA ESTÁ VACÍA O ES MUY CORTA
            } else if (userPassword.isEmpty() || userPassword.length() < 6) {
                inputPassword.setError(getString(R.string.txtWrongPasswordInput));
                inputPassword.setText("");
                inputPasswordConfirmation.setText("");


                // VERIFICAMOS QUE AMBAS CONTRASEÑAN COINCIDAN
            } else if (!userPassword.equals(userPasswordConfirmation)) {
                inputPasswordConfirmation.setError(getString(R.string.txtWrongConfirmationInput));
                inputPassword.setText("");
                inputPasswordConfirmation.setText("");


                // SI TODAS LAS VERIFICACIONES SON CORRECTAS
            } else {
                registerProgress.setMessage(getString(R.string.txtProgressDialogMessage));
                registerProgress.setTitle(getString(R.string.txtProgressDialogTitle));
                registerProgress.setCanceledOnTouchOutside(false);
                registerProgress.show();

                // SE REALIZA LA PETICIÓN PARA REGISTRAR UN USUARIO
                registerAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            registerProgress.dismiss();
                            showSuccessfulToast();

                            Intent loginScreen = new Intent(com.archivo.MainMenu.Login_RegisterActivity.RegisterActivity.this, LoginActivity.class);
                            loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(loginScreen);

                        } else {
                            registerProgress.dismiss();
                            showUnsuccessfulToast();
                        }
                    }
                });
            }

        }

       /*
        // EVENTO QUE SE EJECUTA CUANDO SE DA CLICK EN btnRegister
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SE CONVIERTE A STRING CADA VALOR INGRESADO
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();
                passwordConfirmation = txt_confirmPassword.getText().toString();

                if(!password.equals(passwordConfirmation)){

                    showUnsuccessfulToast("La contraseña y su confirmación deben coincidir");

                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){ // SI DEVUELVE 'success' ES PORQUE SE PUDO REGISTRAR EN LA BASE DE DATOS
                                    showSuccessfulToast("Registro exitoso");

                                    // LLAMADO A LA PANTALLA DE LOGIN
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else{
                                    // DEVUELVE EL ERROR
                                    showUnsuccessfulToast(response);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        // SE INGRESAN LOS DATOS DE CADA PARAMETRO DEL QUERY PHP
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                // SE AÑADE EL QUERY DE REGISTRO
                queue.add(stringRequest);

            }
        });

        */
    }



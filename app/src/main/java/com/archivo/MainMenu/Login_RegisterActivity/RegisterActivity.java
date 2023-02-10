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
import androidx.core.content.ContextCompat;

import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    private EditText inputUserName,inputEmail,inputPassword,inputPasswordConfirmation;

    private Button btnRegister;

    private TextView existingUser;
    /* String name, email, password, passwordConfirmation; */

    private ProgressDialog registerProgress;


    private FirebaseAuth registerAuth;
    private FirebaseUser registerUser;

    //private static final String URL1 = "http://152.231.173.118/usuarios/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Cambia el color del status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // SE IDENTIFICA CADA VARIABLE JAVA CON LA UI
        inputUserName = findViewById(R.id.txtName);
        inputEmail = findViewById(R.id.txtNewEmail);
        inputPassword = findViewById(R.id.txtRegisterPassword);
        inputPasswordConfirmation = findViewById(R.id.txtPasswordConfirmation);

        btnRegister = findViewById(R.id.btnRegisterAccount);

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
        registerProgress = new ProgressDialog(RegisterActivity.this);

        registerAuth = FirebaseAuth.getInstance();
        registerUser = registerAuth.getCurrentUser();
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

    /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // MÉTODO PARA REALIZAR EL REGISTRO
    private void performEmailRegister(){

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
            registerAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful() ){
                        registerProgress.dismiss();
                        showSuccessfulToast();

                        Intent loginScreen = new Intent(RegisterActivity.this, LoginActivity.class);
                        loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(loginScreen);

                    }else{
                        registerProgress.dismiss();
                        showUnsuccessfulToast();
                    }
                }
            });
        }

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
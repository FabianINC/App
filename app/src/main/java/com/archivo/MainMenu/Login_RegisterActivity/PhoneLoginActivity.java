package com.archivo.MainMenu.Login_RegisterActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.archivo.app.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    private EditText inputPhoneNumber;
    private Button btnGenerateOTP;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        //Cambia el color del status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        inputPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnGenerateOTP = findViewById(R.id.btnGenerateOTP);


        /* SE CREAN LOS LISTENERS DE CADA ACCIÓN */
        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnGenerateOTPClicked) {
                performPhoneNumberLogin();
            }
        });


    }

    // MÉTODO PARA REALIZAR EL LOGIN MEDIANTE NÚMERO TELEFONICO (PARTE 1)
    public void performPhoneNumberLogin(){

        // SE OBTIENE EL DATO INGRESADO
        final String userPhoneNumber = inputPhoneNumber.getText().toString();

        // SE VERIFICA QUE EL DATO NO ESTÉ VACIO
        if(userPhoneNumber.equals("")) {
            //inputPhoneNumber.setError(R.string.txtWrongNumberInput);

        //SE VERIFICA QUE EL NÚMERO INGRESADO SEA CORRECTO ( LIBERTY - CLARO -  KOLBI )
        }else if( !(userPhoneNumber.startsWith("6")) && !(userPhoneNumber.startsWith("7")) && !(userPhoneNumber.startsWith("8")) ){
            //inputPhoneNumber.setError(R.string.txtWrongNumberInput);

        // SE VERIFICA LA LONGUITUD DEL NÚMERO
        }else if( userPhoneNumber.length() != 8){
            //inputPhoneNumber.setError(R.string.txtWrongNumberInput);

        // SI TODAS LAS VERIFICACIONES SON CORRECTAS SE INICIA OTRA ACTIVIDAD
        }else{
            ProgressDialog loginProgress = new ProgressDialog(PhoneLoginActivity.this);
            loginProgress.setMessage( getString(R.string.loginProgressDialogText) );// MENSAJE
            loginProgress.setTitle( getString(R.string.loginProgressDialogTitle) ); // TITULO
            loginProgress.setCanceledOnTouchOutside(false);
            loginProgress.show();

            //CONFIGURANDO EL SERVICIO TELEFONICO
            PhoneAuthProvider.getInstance().verifyPhoneNumber(

                    "+506" + userPhoneNumber,
                    60, TimeUnit.SECONDS,
                    PhoneLoginActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        // SI LA VERIFICACIÓN ES CORRECTA
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {}

                        // SI LA VERIFICACION ES INCORRECTA
                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {}

                        // ACCIONES A REALIZAR DESPUÉS DE QUE SE ENVÍA EL CÓDIGO
                        @Override
                        public void onCodeSent(@NonNull String codeOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            //SE LANZA UNA NUEVA ACTIVIDAD
                            Intent verifyOTPScreen = new Intent(PhoneLoginActivity.this, PhoneLoginVerificationActivity.class);
                            verifyOTPScreen.putExtra("userPhoneNumber", userPhoneNumber); // SE ENVÍA EL NÚMERO
                            verifyOTPScreen.putExtra("codeOTP", codeOTP);// SE ENVÍA EL CÓDIGO OTP

                            loginProgress.dismiss();
                            startActivity(verifyOTPScreen);
                        }
                    });
        }
    }

}
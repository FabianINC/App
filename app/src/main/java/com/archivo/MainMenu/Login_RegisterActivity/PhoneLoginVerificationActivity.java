package com.archivo.MainMenu.Login_RegisterActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.archivo.MainMenu.MainActivity;
import com.archivo.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginVerificationActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    private Button btnVerifyOTP;
    private EditText codeDigit1, codeDigit2, codeDigit3, codeDigit4, codeDigit5, codeDigit6;
    private TextView txtMessageInstructions, resendCode;

    private String codeOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login_verification);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        resendCode = findViewById(R.id.txtResendOTP);

        codeDigit1 = findViewById(R.id.txtCode1);
        codeDigit2 = findViewById(R.id.txtCode2);
        codeDigit3 = findViewById(R.id.txtCode3);
        codeDigit4 = findViewById(R.id.txtCode4);
        codeDigit5 = findViewById(R.id.txtCode5);
        codeDigit6 = findViewById(R.id.txtCode6);

        codeOTP = getIntent().getStringExtra("codeOTP"); // SE OBTIENE EL CÓDIGO ENVIADO

        txtMessageInstructions = findViewById(R.id.txtMessageInstructions);

        // SE 'PERSONALIZA' EL MENSAJE INSTRUCTIVO
        txtMessageInstructions.setText(String.format(R.string.txtOTPInstructions + " +506 %s", getIntent().getStringExtra("userPhoneNumber")));

       // LISTENER PARA CUANDO SE PRESIONE EL BOTÓN DE 'VERIFICAR'
        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOTPVerification();
            }
        });

        // LISTENER PARA CUANDO SE NECESITE REENVIAR EL CODIGO DE VERIFICACIÓN
        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode();
            }
        });

        nextInput();
    }

    // MÉTODO PARA VERIFICAR EL CÓDIGO INGRESADO
    private void performOTPVerification(){

        final String inputOTP = codeDigit1.getText().toString()
                + codeDigit2.getText().toString()
                    + codeDigit3.getText().toString()
                        + codeDigit4.getText().toString()
                            + codeDigit5.getText().toString()
                                + codeDigit6.getText().toString() ;


        //SE VERIFICA QUE NO ESTÉ VACÍO O QUE NO SEA MÁS CORTO
        if( inputOTP.equals("") || (inputOTP.length() != 6)){
            Toast.makeText(this, R.string.toastWrongCodeInput, Toast.LENGTH_SHORT).show();

        // SI LA VERIFICACIÓN ES CORRECTA SE PROCEDE A VERIFICAR EL CODIGO ENVIADO
        }else{

            // SE VERIFICA QUE EL CÓDIGO NO SEA NULO
            if( codeOTP != null){

                // SE OBTIENEN AMBOS CODIGOS
                PhoneAuthCredential phoneCredential = PhoneAuthProvider.getCredential(codeOTP, inputOTP);

                // SE REALIZA EL INICIO DE SESIÓN
                FirebaseAuth.getInstance().signInWithCredential(phoneCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if( task.isSuccessful() ){
                                    Toast.makeText(PhoneLoginVerificationActivity.this, "Hola", Toast.LENGTH_SHORT).show();

                                    Intent mainScreen = new Intent(PhoneLoginVerificationActivity.this, MainActivity.class);
                                    mainScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainScreen);
                                }else{
                                    Toast.makeText(PhoneLoginVerificationActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                Toast.makeText(this, "internet coneccction", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //MÉTODO PARA PASAR AL SIGUIENTE CAMNPO DE TEXTO NECESARIO
    private void nextInput(){

        // INPUT #1
        codeDigit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // SI NO ESTÁ VACÍO SOLICITA PASAR AL SIGUIENTE
                if( !charSequence.toString().trim().isEmpty() ){
                    codeDigit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // INPUT #2
        codeDigit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // SI NO ESTÁ VACÍO SOLICITA PASAR AL SIGUIENTE
                if( !charSequence.toString().trim().isEmpty() ){
                    codeDigit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // INPUT #3
        codeDigit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // SI NO ESTÁ VACÍO SOLICITA PASAR AL SIGUIENTE
                if( !charSequence.toString().trim().isEmpty() ){
                    codeDigit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // INPUT #4
        codeDigit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // SI NO ESTÁ VACÍO SOLICITA PASAR AL SIGUIENTE
                if( !charSequence.toString().trim().isEmpty() ){
                    codeDigit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // INPUT #5
        codeDigit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // SI NO ESTÁ VACÍO SOLICITA PASAR AL SIGUIENTE
                if( !charSequence.toString().trim().isEmpty() ){
                    codeDigit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    // MÉTODO PARA REENVIAR EL CÓDIGO
    private void resendCode(){

        String userPhoneNumber = getIntent().getStringExtra("userPhoneNumber");

        //CONFIGURANDO EL SERVICIO TELEFONICO
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+506" + userPhoneNumber,
                60, TimeUnit.SECONDS,
                PhoneLoginVerificationActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    // SI LA VERIFICACIÓN ES CORRECTA
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(PhoneLoginVerificationActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    }

                    // SI LA VERIFICACION ES INCORRECTA
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(PhoneLoginVerificationActivity.this, "Failed, internet", Toast.LENGTH_SHORT).show();
                    }

                    // ACCIONES A REALIZAR DESPUÉS DE QUE SE ENVÍA EL CÓDIGO
                    @Override
                    public void onCodeSent(@NonNull String newCodeOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        codeOTP = newCodeOTP;
                        Toast.makeText(PhoneLoginVerificationActivity.this, "Codigo reenviado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
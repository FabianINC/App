package Login_RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.archivo.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    EditText input_phoneNumber, input_OTP;

    Button btnGenerateOTP, btnVerifyOTP;

    FirebaseAuth phoneAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        input_phoneNumber = findViewById(R.id.txtPhoneNumber);
        input_OTP = findViewById(R.id.txtOTP);

        btnGenerateOTP = findViewById(R.id.btnGenerateOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);

        /* SE CREAN LOS LISTENERS DE CADA ACCIÓN */

        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btnGenerateOTPClicked) {

            }
        });


    }

    // MÉTODO PARA GENERAR EL CÓDIGO DE VERIFICACIÓN
    private void generateOTP(){
        final String phoneNumber = input_phoneNumber.getText().toString();

        //SE VERIFICA QUE EL NÚMERO NO ESTÉ VACIO
        if( phoneNumber.isEmpty() ){
            Toast.makeText(this, "NUMERO INCORRECTO", Toast.LENGTH_SHORT).show();

        }else{

        }
    }


    public void performPhoneNumberLogin(View verifyNumberClicked){

    }

}
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

    }

    // MÉTODO PARA GENERAR EL CÓDIGO DE VERIFICACIÓN
    public void generateOTP(){
        final String phoneNumber = input_phoneNumber.getText().toString();

        //SE VERIFICA QUE EL NÚMERO NO ESTÉ VACIO
        if( phoneNumber.isEmpty() ){
            Toast.makeText(this, "NUMERO INCORRECTO", Toast.LENGTH_SHORT).show();

        }else{
            // SE CONFIGURA EL SERVICIO DE FIREBASE
            PhoneAuthOptions phoneOptions =
                    PhoneAuthOptions.newBuilder(phoneAuth)
                            .setPhoneNumber("+506" + phoneNumber) // NÚMERO A VERIFICAR
                            .setTimeout(60L, TimeUnit.SECONDS) // TIEMPO DE DESCONEXIÓN
                            .setActivity(this)
                            .setCallbacks(mCallbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(phoneOptions);
        }
    }


    public void performPhoneNumberLogin(View verifyNumberClicked){

    }

}
package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
import com.archivo.MainMenu.Login_RegisterActivity.RegisterActivity;
import com.archivo.MainMenu.MainActivity;
import com.archivo.app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GoogleLogin extends LoginActivity {


    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_google_login);

        //CONFIGURANDO EL SERVICIO DE GOOGLE
        GoogleSignInOptions googleSignIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string
                        .default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(GoogleLogin.this, googleSignIn);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void onActivityResult(int googleRequestCode, int googleResultCode, Intent googleIntent){
        //Toast.makeText(this, "OnActivityResult", Toast.LENGTH_SHORT).show();
        super.onActivityResult(googleRequestCode, googleResultCode, googleIntent);

        //SE VERIFICA EL RESULTADO DEVUELTO DEL INTENT DEL LOGIN MEDIANTE GOOGLE
        if( googleRequestCode == RC_SIGN_IN ){
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(googleIntent);

            try{
                GoogleSignInAccount userAccount = signInTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(userAccount.getIdToken());
            }catch(ApiException apiError){
                Toast.makeText(this, "apiError.getMessage()", Toast.LENGTH_SHORT).show();
                showUnsuccessfulToast();
            }
        }
    }


    FirebaseFirestore registerFireStoreAuth;
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential userCredential = GoogleAuthProvider.getCredential(idToken, null);

        FirebaseAuth loginAuth = FirebaseAuth.getInstance();;
        FirebaseUser loginUser = loginAuth.getCurrentUser();;

        loginAuth.signInWithCredential(userCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser signedUser = loginAuth.getCurrentUser();
                            showSuccessfulToast();

                            Intent loginScreen = new Intent(GoogleLogin.this, MainActivity.class);
                            startActivity(loginScreen);

                        }else{
                            showUnsuccessfulToast();
                        }
                    }
                });
    }

}
package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;


import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
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

public class GoogleLogin extends AppCompatActivity {

    LoginActivity loginActivityScreen;

    private static final int RC_SIGN_IN = 101;
    public void performGoogleLogin(LoginActivity loginActivity){

        loginActivityScreen = loginActivity;
        //CONFIGURANDO EL SERVICIO DE GOOGLE
        GoogleSignInOptions googleSignIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string
                        .default_web_client_id))
                        .requestEmail()
                        .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(loginActivityScreen, googleSignIn);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent){
        super.onActivityResult(requestCode, resultCode, dataIntent);

        //SE VERIFICA EL RESULTADO DEVUELTO DEL INTENT DEL LOGIN MEDIANTE GOOGLE
        if( requestCode == RC_SIGN_IN ){
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(dataIntent);

            try{
                GoogleSignInAccount userAccount = signInTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(userAccount.getIdToken());
            }catch(ApiException apiError){
                //showUnsuccessfulToast("Ha ocurrido un problema a la hora de conectarse");
            }
        }
    }

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

                            //showSuccessfulToast("¡Bienvenido!");

                            Intent mainScreen = new Intent(loginActivityScreen, MainActivity.class);
                            mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainScreen);

                        }else{
                            //showUnsuccessfulToast("No se ha podido iniciar sesión");
                        }
                    }
                });
    }
}

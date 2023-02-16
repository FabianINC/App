package com.archivo.MainMenu.Login_RegisterActivity.LoginMethods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.archivo.MainMenu.Login_RegisterActivity.LoginActivity;
import com.archivo.MainMenu.MainActivity;
import com.archivo.app.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class FacebookLogin extends LoginActivity {


    private CallbackManager facebookCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);


       facebookCallBack = CallbackManager.Factory.create();

        //SE OTORGAN PERMISOS
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(facebookCallBack,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int facebookRequestCode, int facebookResultCode, Intent facebookIntent){
        super.onActivityResult(facebookRequestCode, facebookResultCode, facebookIntent);

        facebookCallBack.onActivityResult(facebookRequestCode, facebookResultCode, facebookIntent);
    }

    FirebaseAuth facebookAuth;

    private void handleFacebookAccessToken(AccessToken facebookToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(facebookToken.getToken());
        facebookAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            showSuccessfulToast();

                            FirebaseUser facebookUser = facebookAuth.getCurrentUser();

                            //SE HACE EL LLAMADO A OTRA ACTIVIDAD
                            Intent mainScreen = new Intent(FacebookLogin.this, MainActivity.class);
                            mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainScreen);

                        } else {
                            showUnsuccessfulToast();
                        }
                    }
                });
    }
}
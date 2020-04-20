package platzi.com.platzigramnew.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import platzi.com.platzigramnew.R;
import platzi.com.platzigramnew.login.presenter.LoginPresenter;
import platzi.com.platzigramnew.login.presenter.LoginPresenterImpl;
import platzi.com.platzigramnew.view.ConteinerActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText username;
    private TextInputEditText password;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;
    private LoginButton loginButtonFacebook;

    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.log("Iniciando" + TAG);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        username = (TextInputEditText)findViewById(R.id.etUsername);
        password = (TextInputEditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.login);
        loginButtonFacebook = (LoginButton)findViewById(R.id.login_facebook);
        progressBarLogin = (ProgressBar)findViewById(R.id.progressBarLogin);
        hideProgressBar();

        presenter = new LoginPresenterImpl(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail());
                    goHome();
                }else {
                    Log.w(TAG,"Usuario no logeado");
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(username.getText().toString(),password.getText().toString());

            }
        });

        loginButtonFacebook.setReadPermissions(Arrays.asList("email"));
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG,"Facebook login success Token:" + loginResult.getAccessToken().getApplicationId());
                signInFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.w(TAG,"Facebook login cancelado:");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG,"Facebook login Error:" + error.toString());
                error.printStackTrace();
                Crashlytics.logException(error);
            }
        });
    }

    private void signInFacebookFirebase(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();

                    SharedPreferences preferences = getSharedPreferences("USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email",user.getEmail());
                    editor.commit();
                    goHome();
                    Crashlytics.log(Log.WARN,TAG,"Login Facebook Exitoso");
                    Toast.makeText(LoginActivity.this,"Login Facebook Exitoso",Toast.LENGTH_SHORT).show();
                }else{
                    Crashlytics.log(Log.WARN,TAG,"Login Facebook NO Exitoso");
                    Toast.makeText(LoginActivity.this,"Login Facebook NO Exitoso",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void signIn(String username, String password) {
        presenter.signIn(username,password,this,firebaseAuth);
    }

    public void goCreateAccount(View view){
        goCreateAccount();
    }

    public void goHome(View view){
        goHome();
    }


    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, ConteinerActivity.class);
        startActivity(intent);
    }

    @Override
    public void goWeb() {
        Intent intent = new Intent("android.intent.action.WEB_SEARCH");
        intent.setData(Uri.parse("https://www.instagram.com/?hl=es-la"));
        startActivity(intent);
    }

    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        btnLogin.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        btnLogin.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this,getString(R.string.login_error) + error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}

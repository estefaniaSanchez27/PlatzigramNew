package platzi.com.platzigramnew.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import platzi.com.platzigramnew.R;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnJoinUs;
    private TextInputEditText edtemail;
    private TextInputEditText edtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.log("Iniciando" + TAG);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_title_create_account),true);

        btnJoinUs = (Button)findViewById(R.id.joinUs);
        edtemail = (TextInputEditText)findViewById(R.id.etEmail);
        edtpassword = (TextInputEditText)findViewById(R.id.etPassword_createAccount);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail());
                    Crashlytics.log(Log.WARN,TAG,"Usuario logeado" + firebaseUser.getEmail());

                }else {
                    //Log.w(TAG,"Usuario no logeado");
                    Crashlytics.log(Log.WARN,TAG,"Usuario no logeado");

                }
            }
        };

        btnJoinUs.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this,"Cuenta creada exitosamente",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateAccountActivity.this,"Ocurrio un error al crear la cuenta",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void showToolbar(String titulo, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
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

}

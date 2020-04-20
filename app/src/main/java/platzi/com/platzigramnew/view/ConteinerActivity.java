package platzi.com.platzigramnew.view;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import platzi.com.platzigramnew.R;
import platzi.com.platzigramnew.login.view.LoginActivity;
import platzi.com.platzigramnew.post.view.HomeFragment;
import platzi.com.platzigramnew.view.fragments.ProfileFragment;
import platzi.com.platzigramnew.view.fragments.SearchFragment;

public class ConteinerActivity extends AppCompatActivity {

    private static final String TAG = "ConteinerActivity";
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.log("Iniciaando" + TAG);
        setContentView(R.layout.activity_conteiner);

        firebaseInitialize();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.home);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.home:
                        homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flconteiner,homeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.profile:
                        profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flconteiner,profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.search:
                        searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flconteiner,searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
    }

    private void firebaseInitialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail());
                }else {
                    Log.w(TAG,"Usuario no logeado");
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.mSignOut:
                firebaseAuth.signOut();

                if(LoginManager.getInstance() != null){
                    LoginManager.getInstance().logOut();
                }

                Toast.makeText(this,"Se cerro la sesión",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ConteinerActivity.this, LoginActivity.class);
                startActivity(i);

                break;
            case R.id.mAbout:
                Toast.makeText(this,"Platzigram By: Platzi",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

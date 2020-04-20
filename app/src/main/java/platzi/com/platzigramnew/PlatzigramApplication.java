package platzi.com.platzigramnew;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlatzigramApplication extends Application {

    private static final String TAG = "PlatzigramApplication";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics.log("Inicializando variables en PlatzigramApplication");

        FacebookSdk.sdkInitialize(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail()); mensaje que aparece en logcat de android studio
                    Crashlytics.log(Log.WARN,TAG,"Usuario logeado" + firebaseUser.getEmail());
                }else {
                    //Log.w(TAG,"Usuario no logeado");
                    Crashlytics.log(Log.WARN,TAG,"Usuario no logeado");
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();
    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}

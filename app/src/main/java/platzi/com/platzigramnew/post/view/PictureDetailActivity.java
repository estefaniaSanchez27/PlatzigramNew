package platzi.com.platzigramnew.post.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import platzi.com.platzigramnew.PlatzigramApplication;
import platzi.com.platzigramnew.R;

public class PictureDetailActivity extends AppCompatActivity {

    private ImageView imageHeader;
    private PlatzigramApplication app;
    private StorageReference storageReference;
    private String PHOTO_NAME = "fany.jpg";
    private String TAG = "PictureDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.log("Iniciando" + TAG);
        setContentView(R.layout.activity_picture_detail);

        app = (PlatzigramApplication) getApplicationContext();
        storageReference = app.getStorageReference();

        imageHeader = (ImageView)findViewById(R.id.imageHeader);


        showToolbar("",true);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setExitTransition(new Fade());
        }

        showData();
    }

    private void showData() {
        storageReference.child("postImages/"+PHOTO_NAME)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(PictureDetailActivity.this).load(uri.toString()).into(imageHeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PictureDetailActivity.this,"Ocurrio un error al traer la foto",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Crashlytics.logException(e);
            }
        });
    }

    public void showToolbar(String titulo, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

}

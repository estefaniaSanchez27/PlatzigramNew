package platzi.com.platzigramnew.post.view;


import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import platzi.com.platzigramnew.R;
import platzi.com.platzigramnew.adapter.PictureAdapterRecycleView;
import platzi.com.platzigramnew.model.Picture;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";
    private String TAG="HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Crashlytics.log("Iniciando" + TAG);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        showToolbar("Home",true,view);
        RecyclerView pictureRecycle = (RecyclerView) view.findViewById(R.id.pictureRecycle);
        fabCamera = (FloatingActionButton)view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycle.setLayoutManager(linearLayoutManager);

        PictureAdapterRecycleView pictureAdapterRecycleView = new PictureAdapterRecycleView(builPicture(),R.layout.cardview_picture,getActivity());
        pictureRecycle.setAdapter(pictureAdapterRecycleView);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return view;
    }

    private void takePicture() {

        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentTakePicture.resolveActivity(getActivity().getPackageManager())!=null){

            File photofile = null;

            try{
                photofile = createImageFile();
            }catch (Exception e){
                e.printStackTrace();
                Crashlytics.logException(e);
            }

            if(photofile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(),"platzi.com.platzigramnew",photofile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }

        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFilename = "JPEG" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photo = File.createTempFile(imageFilename, ".jpg",storageDir);

        photoPathTemp = "file" + photo.getAbsolutePath();

        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("Home Fragment","CAMERA OK!! :)");
            Intent i = new Intent(getActivity(),NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP",photoPathTemp);
            startActivity(i);
        }
    }

    public ArrayList<Picture> builPicture(){
        ArrayList<Picture> pic = new ArrayList<>();
        pic.add(new Picture("https://images-na.ssl-images-amazon.com/images/I/71KNhm5-zSL._SY450_.jpg", "Uriel Ramirez", "4 dias", "3 Me Gusta"));
        pic.add(new Picture("https://ae01.alicdn.com/kf/HTB1MDUyXfBNTKJjSszeq6Au2VXal/Beibehang-hermosas-cascadas-agua-rico-paisaje-natural-TV-Fondo-pared-personalizada-grande-fresco-papel-pintado-verde.jpg",
                "Juan Pablo", "3 dias", "10 Me Gusta"));
        pic.add(new Picture("https://www.hipershop.es/im%C3%A1genes/Sucsaistat/Sucsaistat-Decoracion-para-El-Hogar-Papel-Tapiz-3D-Mural-De-Pared-Naturaleza-Fresca-Cielo-Azul-Y-Nubes-Blancas-Pintura-De-Pared-para-Sala-De-Estar-TV-Telon-De-Fondo,-250-+-175-Cm-1380170369.jpg",
                "Anahi Salgado", "2 dias", "9 Me Gusta"));
        return pic;
    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}

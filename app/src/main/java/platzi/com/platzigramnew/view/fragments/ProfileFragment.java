package platzi.com.platzigramnew.view.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import platzi.com.platzigramnew.R;
import platzi.com.platzigramnew.adapter.PictureAdapterRecycleView;
import platzi.com.platzigramnew.model.Picture;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private String TAG="rofileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Crashlytics.log("Iniciando" + TAG);
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile,container,false);
        showToolbar("",false,view);
        RecyclerView pictureRecycle = (RecyclerView) view.findViewById(R.id.pictureProfileRecycle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycle.setLayoutManager(linearLayoutManager);

        PictureAdapterRecycleView pictureAdapterRecycleView = new PictureAdapterRecycleView(builPicture(),R.layout.cardview_picture,getActivity());
        pictureRecycle.setAdapter(pictureAdapterRecycleView);
        return view;
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}

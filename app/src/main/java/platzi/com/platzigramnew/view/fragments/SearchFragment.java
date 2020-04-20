package platzi.com.platzigramnew.view.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
public class SearchFragment extends Fragment {


    private String TAG="SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Crashlytics.log("Iniciando" + TAG);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.pictureSearchRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        PictureAdapterRecycleView adapter = new PictureAdapterRecycleView(builPicture(),R.layout.cardview_picture,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }

    public ArrayList<Picture> builPicture(){
        ArrayList<Picture> pic = new ArrayList<>();
        pic.add(new Picture("https://i.ytimg.com/vi/hW9PUzl7j9w/maxresdefault.jpg", "Paisaje 1", "1 dia", "10"));
        pic.add(new Picture("https://mott.pe/noticias/wp-content/uploads/2016/11/Janette-Asche.jpg", "Paisaje 2", "2 dia", "20"));
        pic.add(new Picture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7B_XkmN1JqS-d5rh70xwycsTCEPS8TcZFEQ3qKI2jmDFh_opE",
                "Paisaje 3", "1 dia", "10"));
        return pic;
    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}

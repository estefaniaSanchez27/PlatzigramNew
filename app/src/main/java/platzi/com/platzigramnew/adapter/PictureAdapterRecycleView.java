package platzi.com.platzigramnew.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import platzi.com.platzigramnew.R;
import platzi.com.platzigramnew.model.Picture;
import platzi.com.platzigramnew.post.view.PictureDetailActivity;

public class PictureAdapterRecycleView extends RecyclerView.Adapter<PictureAdapterRecycleView.PictureViewHolder> {

    private ArrayList<Picture> pictures;
    private int resourse;
    private Activity activity;

    public PictureAdapterRecycleView(ArrayList<Picture> pictures,int resourse,Activity activity){
        this.pictures=pictures;
        this.resourse=resourse;
        this.activity=activity;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourse,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);
        holder.usernameCard.setText(picture.getUsername());
        holder.timeCard.setText(picture.getTime());
        holder.likeNumberCard.setText(picture.getLikenumber());
        Picasso.with(activity).load(picture.getPicture()).into(holder.pictureCard);

        holder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PictureDetailActivity.class);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity,v,activity.getString(R.string.transitioname_picture)).toBundle());

                }else {
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{

        private ImageView pictureCard;
        private TextView usernameCard;
        private TextView timeCard;
        private TextView likeNumberCard;


        public PictureViewHolder(View itemView) {
            super(itemView);
            pictureCard     = (ImageView) itemView.findViewById(R.id.imageCard);
            usernameCard    = (TextView) itemView.findViewById(R.id.tvusernameCard);
            timeCard        = (TextView) itemView.findViewById(R.id.timeCard);
            likeNumberCard  = (TextView) itemView.findViewById(R.id.likeNumerCard);

        }
    }
}

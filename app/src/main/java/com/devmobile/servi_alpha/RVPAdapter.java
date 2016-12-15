package com.devmobile.servi_alpha;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Souhail on 03/01/2016.
 */
public class RVPAdapter extends RecyclerView.Adapter<RVPAdapter.PersonViewHolder> {

    List<Post> offers;
    RVPAdapter(List<Post>offers){
        this.offers=offers;
    }
    @Override
    public RVPAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.Title.setText(offers.get(i).title);
        personViewHolder.desc.setText(offers.get(i).desc);
        personViewHolder.personPhoto.setImageResource(offers.get(i).photoID);

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Title;
        TextView desc;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            Title = (TextView) itemView.findViewById(R.id.person_name);
            desc = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

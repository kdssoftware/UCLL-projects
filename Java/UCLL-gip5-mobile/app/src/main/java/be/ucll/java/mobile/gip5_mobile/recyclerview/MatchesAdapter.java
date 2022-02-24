package be.ucll.java.mobile.gip5_mobile.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import be.ucll.java.mobile.gip5_mobile.R;
import be.ucll.java.mobile.gip5_mobile.models.Wedstrijd;

public class MatchesAdapter extends RecyclerView.Adapter<MatchHolder> {
    private static final String TAG = "MatchAdapter";

    private ClickHandler clickHandler;
    private List<Wedstrijd> list;

    public MatchesAdapter(ClickHandler clickHandler, List<Wedstrijd> list) {
        this.clickHandler = clickHandler;
        this.list = list;
    }

    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_item, viewGroup, false);

        return new MatchHolder(v, clickHandler);
    }

    // Voor elk item in de List van de adapter wordt deze method aangeroepen
    // om de 'Data' te visualiseren als 1 lijn of kaartje in de recyclerview
    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + list.get(position));
        Wedstrijd w = list.get(position);
        //System.out.println(list.get(position).getClass());
        holder.setWedstrijd(w);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

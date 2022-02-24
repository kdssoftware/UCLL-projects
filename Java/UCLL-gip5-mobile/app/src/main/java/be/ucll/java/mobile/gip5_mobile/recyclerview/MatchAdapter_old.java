package be.ucll.java.mobile.gip5_mobile.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.ucll.java.mobile.gip5_mobile.R;
import be.ucll.java.mobile.gip5_mobile.models.Wedstrijd;

public class MatchAdapter_old extends RecyclerView.Adapter<MatchAdapter_old.MatchHolder> {
    private static final String TAG = "MatchAdapter";

    private Context context;
    private Wedstrijd wedstrijd;
    private List<Wedstrijd> list;
    private TextView matchName, matchDate;


    public MatchAdapter_old(Context ct, List<Wedstrijd> list){
        context = ct;
        this.list = list;

    }
    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.match_item, parent, false);
        return new MatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+ list.get(position));
        Wedstrijd w = list.get(position);
        //use set match function
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MatchHolder extends RecyclerView.ViewHolder {

        private TextView matchName, matchDate;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            matchName = itemView.findViewById(R.id.lblMatchName);
            matchDate = itemView.findViewById(R.id.lblMatchDate);
        }
    }

    public void setMatch(Wedstrijd w){
        this.wedstrijd = w;
        if (wedstrijd != null){


        }
    }
}

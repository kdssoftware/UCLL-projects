package be.ucll.java.mobile.gip5_mobile.recyclerview;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import be.ucll.java.mobile.gip5_mobile.LoginActivity;
import be.ucll.java.mobile.gip5_mobile.MainActivity;
import be.ucll.java.mobile.gip5_mobile.PlayerDetailsActivity;
import be.ucll.java.mobile.gip5_mobile.R;


import be.ucll.java.mobile.gip5_mobile.models.Wedstrijd;


public class MatchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "MatchHolder";

    private TextView matchName;
    private TextView matchDate;
    private SharedPreferences preferences;


    private Wedstrijd wedstrijd;

    private final ClickHandler clickHandler;

    // Constructor
    public MatchHolder(View itemView, ClickHandler ch) {
        super(itemView);

        this.clickHandler = ch;
        //preferences = getSharedPreferences("PREFERENCE", 0);
        matchName = itemView.findViewById(R.id.lblMatchName);
        matchDate = itemView.findViewById(R.id.lblMatchDate);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

        clickHandler.onMatchClick(wedstrijd);
    }

    public void setWedstrijd(Wedstrijd w) {
        this.wedstrijd = w;
        if (wedstrijd != null) {

            // Set the movie title
            matchName.setText(w.getThuisploeg() + "\n" + w.getTegenstander());

            //System.out.println(wedstrijd.getTijdstip());

            // Set the movie year of release
            //matchDate.setText(LocalDateTime.parse(wedstrijd.getTijdstip(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("YYYY/MM/DD")));
            matchDate.setText(wedstrijd.getTijdstip());


        }
    }

}
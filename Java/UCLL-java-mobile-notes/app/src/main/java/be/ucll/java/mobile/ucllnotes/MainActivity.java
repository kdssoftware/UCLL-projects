package be.ucll.java.mobile.ucllnotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Als alles foutgaat en je wil opnieuw beginnen zo verwijder je de volledige DB
        // this.deleteDatabase(Constants.DB_NAME);
    }

    @Override
    protected void onDestroy() {
        // In een professionele App moet hier code komen die de connectie met de databank vrijgeeft

        super.onDestroy();
    }
}
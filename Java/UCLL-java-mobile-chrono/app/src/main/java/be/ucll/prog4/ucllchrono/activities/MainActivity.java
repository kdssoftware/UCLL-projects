package be.ucll.prog4.ucllchrono.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.ucll.prog4.ucllchrono.R;
import be.ucll.prog4.ucllchrono.utils.DateTimeUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UCLL Chrono";

    // Widgets
    private TextView lblTime;
    private Button btnStart;
    private Button btnStop;

    private long startTime;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblTime = this.findViewById(R.id.lblTime);
        btnStart = this.findViewById(R.id.btnStart);
        btnStop = this.findViewById(R.id.btnStop);

        btnStop.setEnabled(false);

        running = false;
    }

    public void handleClickBtnStart(View view) {
        // long containing milliseconds since device is up and running
        startTime = SystemClock.elapsedRealtime();
        Log.d(TAG, "Chrono started on " + DateTimeUtils.formatDuration(startTime));

        running = true;
        runOnThread();

        btnStop.setEnabled(true);
        btnStop.setBackground(getDrawable(R.drawable.btn_rounded_enabled));
        //btnStop.setAlpha(1); Goede suggestie Thomas Claes

        btnStart.setEnabled(false);
        btnStart.setBackground(getDrawable(R.drawable.btn_rounded_disabled));
        // btnStart.setAlpha((float) 0.5); Goede suggestie Thomas Claes
    }

    public void handleClickBtnStop(View view) {
        long duration = SystemClock.elapsedRealtime() - startTime;
        Log.d(TAG, "Chrono stopped after " + DateTimeUtils.formatDuration(duration));

        // End the thread
        running = false;

        btnStart.setEnabled(true);
        btnStart.setBackground(getDrawable(R.drawable.btn_rounded_enabled));
        btnStop.setEnabled(false);
        btnStop.setBackground(getDrawable(R.drawable.btn_rounded_disabled));
    }

    private void runOnThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        // Quick update on the UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long duration = SystemClock.elapsedRealtime() - startTime;
                                lblTime.setText(DateTimeUtils.formatDuration(duration));
                            }
                        });

                        // No need to go faster then screen refresh rate
                        // f.i. 60 Hz screen refresh is once every 167 Milliseconds
                        // If we update twice in that range it is more then enough
                        Thread.sleep(80); // update duration every 80 milliseconds.
                    } catch (InterruptedException e) {
                        // Thread woke up unexpected. No problem, just continue
                    }
                }
            }
        });
        t.start();
    }
}

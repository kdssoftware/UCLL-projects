package be.ucll.java.mobile.ucllbackgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = BootCompletedReceiver.class.getSimpleName();

    private static final String UNIQUE_WORK_NAME = "be.ucll.java.mobile.ucllbackgroundservices.CheckServerWorker";
    private static final long REPEAT_INTERVAL_MIN = 15; // The minimum is 15 minutes.

    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Starting after boot");

        // Lot's of null checks since this runs invisibly in the background so it better be very stable
        if (intent != null && intent.getAction() != null) {
            // checking one more time it is all about BOOT_COMPLETED
            if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {

                // Always surround invisible background work with a try catch.
                try {
                    Log.i(TAG, "Initiating the work Manager, every " + REPEAT_INTERVAL_MIN + " minutes");

                    // 1. Retrieve the one and only work manager
                    WorkManager wm = WorkManager.getInstance(context);

                    // 2. Refer to 'Worker' class where in the doWork is coded what needs to be execute
                    //    Set the periodicity or repeat interval
                    PeriodicWorkRequest wr = new PeriodicWorkRequest.Builder(BackGroundWorker.class, REPEAT_INTERVAL_MIN, TimeUnit.MINUTES).build();

                    // 3. Enqueue the request to the work Manager
                    // wm.enqueue(wr);

                    // If the device was in hibernate it is OK to 'forget' about the 'old' enqueued requests
                    //   and just execute the 'latest': enqueueUniquePeriodicWork
                    // Provide a fully qualified unique name
                    // If there is a request 'currently' running KEEP it running instead of the new request.
                    wm.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, wr);

                    // For debugging purposes to check if this effectively started after boot
                    Toast.makeText(context, "UCLL BackGroundWorker request enqueued on Work Manager.", Toast.LENGTH_LONG).show();

                    Log.i(TAG, "Started after boot");
                } catch (Exception e) {
                    Log.e(TAG, "Failed to start on boot", e);
                }

            }
        }
    }
}
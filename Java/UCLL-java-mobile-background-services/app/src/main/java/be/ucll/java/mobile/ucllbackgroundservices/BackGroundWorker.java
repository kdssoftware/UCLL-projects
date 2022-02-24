package be.ucll.java.mobile.ucllbackgroundservices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import be.ucll.java.mobile.ucllbackgroundservices.model.WeatherSearch;

// BackGroundWorker extends the Abstract class 'Worker'
public class BackGroundWorker extends Worker implements Response.Listener, Response.ErrorListener {
    private static final String TAG = BackGroundWorker.class.getSimpleName();

    public static final CharSequence CHANNEL_NAME = "UCLL BG Worker notifs";
    public static final String CHANNEL_ID = "UCLL_NOTIFICATIONS";
    public static final String CHANNEL_DESCRIPTION = "Shows background worker notifications whenever a periodic work request is handled by the work manager";

    public static final int NOTIFICATION_ID = 1;
    public static final CharSequence NOTIFICATION_TITLE = "Current temperature";

    private Context ctx;

    // Constructor
    public BackGroundWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ctx = getApplicationContext();

        try {
            Log.i(TAG, "Submitting Weather Web Service request");

            // Hardgecodeerd op NB/OL van de UCLL Campus Proximus in Heverlee
            submitWeatherSearch(50.846105F, 4.727910F);

            // Alternatief gebaseerd op de 'huidige' locatie als je zin hebt om uit te werken
            /*
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

            LocationManager locationMngr = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            locationMngr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, -1, this);

            MainActivity implements LocationListener
            @Override
            public void onLocationChanged(Location location) {
                // ...
            }
            */

            return Result.success();
        } catch (Throwable throwable) {
            Log.e(TAG, "Error notifying user", throwable);
            return Result.failure();
        }
    }

    @Override
    public void onResponse(Object response) {
        // Cast into Gson JSONObject
        JSONObject jsono = (JSONObject) response;
        WeatherSearch ws = new Gson().fromJson(jsono.toString(), WeatherSearch.class);
        if (ws != null && ws.getWeatherObservation() != null) {
            String temp = ws.getWeatherObservation().getTemperature();
            if (temp != null) {
                String message = "Heverlee: " + temp + " °C";
                Log.i(TAG, "Creating notification with message: " + message);
                createNotification(message, ctx);
            }
        }
    }

    private void createNotification(String message, Context ctx) {
        // Make a NotificationChannel if possible. Only available since API level 26+
        // A channel groups notifications together. It overrides 'old' notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Add the channel
            NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID);
        builder.setSmallIcon(android.R.drawable.btn_star)
               .setContentTitle(NOTIFICATION_TITLE)
               .setContentText(message)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setContentIntent(pi) // Triggers MainActivity to open
               .setAutoCancel(true) // Removes the notification when clicked on it
               .setVibrate(new long[]{1000, 1000});  // Vibrate 2 times 1 second. This requires the VIBRATE Permission declaration in AndroidManifest.xml

        // Show the notification
        NotificationManagerCompat.from(ctx).notify(NOTIFICATION_ID, builder.build());
    }

    // Weather Web Service data
    private static final String URL_GEONAMES_WEATHER_PART1 = "http://api.geonames.org/findNearByWeatherJSON?lat=";
    private static final String URL_GEONAMES_WEATHER_PART2 = "&lng=";
    private static final String URL_GEONAMES_WEATHER_PART3 = "&username=kbogaert";
    private void submitWeatherSearch(float lat, float lng) {
        // Instantiate the RequestQueue for asynchronous operations
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = URL_GEONAMES_WEATHER_PART1 + lat + URL_GEONAMES_WEATHER_PART2 + lng + URL_GEONAMES_WEATHER_PART3;
        Log.d(TAG, "URL: " + url);

        // Prepare the request to be send out towards the REST service OMDB_API_URL..
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        // Add the request to the RequestQueue for asynchronous retrieval on separate thread.
        queue.add(req);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // This is when the call upon the web service remains unanswered or in error
        Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error calling service", error);
    }
}

package be.ucll.java.mobile.gip5_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ucll.java.mobile.gip5_mobile.models.Persoon;
import be.ucll.java.mobile.gip5_mobile.models.Wedstrijd;
import be.ucll.java.mobile.gip5_mobile.models.WedstrijdInterface;
import be.ucll.java.mobile.gip5_mobile.models.Wedstrijden;
import be.ucll.java.mobile.gip5_mobile.recyclerview.ClickHandler;
import be.ucll.java.mobile.gip5_mobile.recyclerview.MatchAdapter_old;
import be.ucll.java.mobile.gip5_mobile.recyclerview.MatchesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ClickHandler, Response.Listener, Response.ErrorListener {
    private RecyclerView recyclerView;
    private MatchesAdapter adapter;
    private static final String TAG = "main activity";
    private static final String WEBSERVICE_API = "http://ucll-team3-gip5-web.eu-west-1.elasticbeanstalk.com";
    private List<Wedstrijd> wedstrijdList;
    private RequestQueue queue;
    private SharedPreferences preferences;
    private Long matchIdPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RvMatches);

        preferences = getSharedPreferences("PREFERENCE", 0);

        matchIdPref = preferences.getLong("MatchIdPref",-1);

        //MatchAdapter_old matchAdapterOld = new MatchAdapter_old(this,wedstrijdList );
        //recyclerView.setAdapter(matchAdapterOld);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        queue = Volley.newRequestQueue(this);
        ClickHandler clickHandler = this; //deze wordt buitenaf gemaakt omdat het wordt gecalled in een functie die buiten de scope is voor het 'this' object.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(WEBSERVICE_API + "/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//         WedstrijdInterface request = retrofit.create(WedstrijdInterface.class);
//        Call<List<Wedstrijd>> call = request.getWedstrijden();
//        call.enqueue(new Callback<List<Wedstrijd>>() {
//            @Override
//            public void onResponse(Call<List<Wedstrijd>> call, retrofit2.Response<List<Wedstrijd>> response) {
//                System.out.println(response.body());
//                //Toast.makeText(MainActivity.this,response.body().toString(),Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Wedstrijd>> call, Throwable t) {
//                Log.e("Error",t.getMessage());
//            }
//
//        });
//  STILL HAVE TO PUT THE DATA FROM THE REQUEST IN A LIST LOOK AT MOVIE
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WEBSERVICE_API + "/rest/v1/wedstrijdMetPloegen?api=" + preferences.getString("ApiPref","Undefined"), null,this,this);

        queue.start();
        queue.add(req);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMatchClick(Wedstrijd wedstrijd) {
        // Does stuff when clicked.

        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("MatchIdPref", wedstrijd.getId());
        editor.apply();
        matchIdPref = preferences.getLong("MatchIdPref",-1);
        System.out.println("Match id form getId: " + wedstrijd.getId());
        Log.d(TAG, "Match id shared Pref: " + matchIdPref);

        Intent intent = new Intent(MainActivity.this, PlayerDetailsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        //niet gebruiken, wordt ook niet gebruikt in de movie search
    }

    @Override
    public void onResponse(Object response) {
        // display response on Success
        Log.d("Response", response.toString());
        Log.d(TAG, "entered respone");
        //JSONArray jsono = (JSONArray) response;
        ArrayList<Wedstrijd> wedstrijdList= new ArrayList<>();
        JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonArray();

        for (JsonElement jsonObjectElement : jsonArray) {
            JsonObject jsonObject = jsonObjectElement.getAsJsonObject();
            Wedstrijd w = new Gson().fromJson(jsonObject, Wedstrijd.class);
            wedstrijdList.add(w);
            //put id's in an list for every match

        }


        // Log the output as debug information
        //Log.d(TAG, jsono.toString());

        // Convert REST String to Pojo's using GSON libraries
        //Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        //ArrayList<Wedstrijd> wwwwedstrijdList = gson.fromJson(jsono.toString(), ArrayList.class);
        Log.d("wedstrijd to string: ", wedstrijdList.toString());



        if (wedstrijdList != null){
            //Wedstrijden gevonden
            adapter = new MatchesAdapter(this, wedstrijdList);

        }else{
            //geen wedstrijden gevonden
        }
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error.Response", error.toString());

    }

}
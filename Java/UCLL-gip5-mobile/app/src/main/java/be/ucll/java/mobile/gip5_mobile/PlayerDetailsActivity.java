package be.ucll.java.mobile.gip5_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import be.ucll.java.mobile.gip5_mobile.models.Deelname;
import be.ucll.java.mobile.gip5_mobile.models.Wedstrijd;

public class PlayerDetailsActivity extends AppCompatActivity /*implements Response.ErrorListener, Response.Listener<JSONObject>*/ {
    private static final String TAG = "PlayerDetaials activity";
    private static final String WEBSERVICE_API = "http://ucll-team3-gip5-web.eu-west-1.elasticbeanstalk.com";
    private RequestQueue queue;
    private SharedPreferences preferences;
    private TextView matchName, matchDate, matchLocation;
    private Long matchIdPref;
    private Long idPref;
    private Deelname deelname;
    private RadioGroup radioGroup;
    private Button messageButton;
    private TextView messageField;
    private Long deelnameID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        matchName = findViewById(R.id.lblDtMatchName);
        matchDate = findViewById(R.id.lblDtMatchTime);
        matchLocation = findViewById(R.id.lblDtMatchLocation);
        radioGroup = findViewById(R.id.rbgPlayerStatus);
        messageButton = findViewById(R.id.btnSendMessagebtn);
        messageField = findViewById(R.id.fldPlayerMessage);

        preferences = getSharedPreferences("PREFERENCE", 0);

        matchIdPref = preferences.getLong("MatchIdPref",-1);
        idPref = preferences.getLong("IdPref", -1);

        queue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WEBSERVICE_API + "/rest/v1/wedstrijdMetPloegen/"+ matchIdPref + "?api=" + preferences.getString("ApiPref","Undefined") , null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Log.d(TAG, "entered respone");
                        JSONObject jsono = (JSONObject) response;

                        // Log the output as debug information
                        Log.d(TAG, jsono.toString());

                        // Convert REST String to Pojo's using GSON libraries
                        Wedstrijd wedstrijd = new Gson().fromJson(jsono.toString(), Wedstrijd.class);
                        Log.d("Person to string: ", wedstrijd.toString());

                        matchName.setText(wedstrijd.getThuisploeg() + "\n" + "\uD83C\uDD9A" + "\n" + wedstrijd.getTegenstander());
                        //matchDate.setText(LocalDateTime.parse(wedstrijd.getTijdstip(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("YYYY/MM/DD")));
                        matchDate.setText(wedstrijd.getTijdstip());
                        matchLocation.setText(wedstrijd.getLocatie());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        //maybe add some user feedback


                    }
                }
        );

        JSONObject postData = new JSONObject();
        try {
            postData.put("persoonId", idPref);
            postData.put("wedstrijdId", matchIdPref);
            postData.put("commentaar", "");
            postData.put("status", 3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.POST, WEBSERVICE_API + "/rest/v1/deelname" + "?api=" + preferences.getString("ApiPref","Undefined") , postData,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Log.d(TAG, "entered deelname respone");
                        JSONObject jsono = (JSONObject) response;

                        // Log the output as debug information
                        Log.d(TAG, jsono.toString());

                        // Convert REST String to Pojo's using GSON libraries
                        deelname = new Gson().fromJson(jsono.toString(), Deelname.class);
                        Log.d("deelname to string: ", deelname.toString());
                        deelnameID = deelname.getId();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        //maybe add some user feedback


                    }
                }
        );


        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Message button pressed");
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String messageText = messageField.getText().toString();
                JSONObject putData = new JSONObject();



                System.out.println("Selected radio button " + selectedId);
                switch (selectedId){
                    case 2131296537:
                        System.out.println("Playing");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 5);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296538:
                        System.out.println("Reserve");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296539:
                        System.out.println("Sick");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 7);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296541:
                        System.out.println("Unavailable");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296536:
                        System.out.println("Invited");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 3);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296542:
                        System.out.println("Uninvited");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 4);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296540:
                        System.out.println("Unanswered");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 8);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296535:
                        System.out.println("Declined");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 6);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2131296534:
                        System.out.println("Available");
                        try {
                            putData.put("persoonId", idPref);
                            putData.put("wedstrijdId", matchIdPref);
                            putData.put("commentaar", messageText);
                            putData.put("status", 0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }

                JsonObjectRequest req3 = new JsonObjectRequest(Request.Method.PUT, WEBSERVICE_API + "/rest/v1/deelname/"+ deelnameID + "?api=" + preferences.getString("ApiPref","Undefined") , putData,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                Log.d(TAG, "entered deelname respone");
                                JSONObject jsono = (JSONObject) response;

                                // Log the output as debug information
                                Log.d(TAG, jsono.toString());

                                // Convert REST String to Pojo's using GSON libraries
                                deelname = new Gson().fromJson(jsono.toString(), Deelname.class);
                                Log.d("deelname to string: ", deelname.toString());


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                                //maybe add some user feedback


                            }
                        }
                );
                queue.add(req3);

            }
        });

        queue.start();
        queue.add(req);
        queue.add(req2);


    }

//    @Override
//    public void onErrorResponse(VolleyError error) {
//        Log.d("Error.Response", error.toString());
//    }
//
//    @Override
//    public void onResponse(JSONObject response) {
//        // display response on Success
//        Log.d("Response", response.toString());
//        Log.d(TAG, "entered respone");
//        JSONObject jsono = (JSONObject) response;
//
//        // Log the output as debug information
//        Log.d(TAG, jsono.toString());
//
//        // Convert REST String to Pojo's using GSON libraries
//        Wedstrijd wedstrijd = new Gson().fromJson(jsono.toString(), Wedstrijd.class);
//        Log.d("Person to string: ", wedstrijd.toString());
//
//        matchName.setText(wedstrijd.getThuisploeg() + "\n" + "\uD83C\uDD9A" + "\n" + wedstrijd.getTegenstander());
//        matchDate.setText(LocalDateTime.parse(wedstrijd.getTijdstip(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("YYYY/MM/DD")));
//        matchLocation.setText(wedstrijd.getLocatie());
//    }
//    public void onBtnMessageClick(View view){
//        Log.d(TAG, "Entered messagebutton");
//
//        System.out.println("Selected radio button " + radioGroup.getCheckedRadioButtonId());
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, WEBSERVICE_API + "/rest/v1/deelname/"  + "/commentaar" + matchIdPref + "?api=" + preferences.getString("ApiPref","Undefined") , null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Response", response.toString());
//                        Log.d(TAG, "entered respone");
//                        JSONObject jsono = (JSONObject) response;
//
//                        // Log the output as debug information
//                        Log.d(TAG, jsono.toString());
//
//                        // Convert REST String to Pojo's using GSON libraries
//                        Wedstrijd wedstrijd = new Gson().fromJson(jsono.toString(), Wedstrijd.class);
//                        Log.d("Person to string: ", wedstrijd.toString());
//
//                        matchName.setText(wedstrijd.getThuisploeg() + "\n" + "\uD83C\uDD9A" + "\n" + wedstrijd.getTegenstander());
//                        matchDate.setText(LocalDateTime.parse(wedstrijd.getTijdstip(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).format(DateTimeFormatter.ofPattern("YYYY/MM/DD")));
//                        matchLocation.setText(wedstrijd.getLocatie());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                        //maybe add some user feedback
//
//
//                    }
//                }
//        );
//
//        queue.start();
//        queue.add(req);

//}

}

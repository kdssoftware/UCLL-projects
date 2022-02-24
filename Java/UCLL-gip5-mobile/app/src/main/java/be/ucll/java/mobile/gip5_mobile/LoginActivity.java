package be.ucll.java.mobile.gip5_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import be.ucll.java.mobile.gip5_mobile.models.Persoon;

public class LoginActivity extends AppCompatActivity /*, ClickHandler*/ {
    private static final String TAG = "login activity";
    private static final String WEBSERVICE_API = "http://ucll-team3-gip5-web.eu-west-1.elasticbeanstalk.com";

    private TextView usernameField;
    private TextView passwordField;
    private Button loginButton;
    private String usernamePref;
    private String passwordPref;
    private String apiPref;
    private String usernameString;
    private String passwordString;
    private long idPref;

    private RequestQueue queue;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("PREFERENCE", 0);

        usernamePref = preferences.getString("UsernamePref","Undefined");
        passwordPref = preferences.getString("PasswordPref","Undefined");
        apiPref = preferences.getString("ApiPref","Undefined");
        idPref = preferences.getLong("IdPref",-1);

        Log.d(TAG,  "usernamePref is " + usernamePref);
        Log.d(TAG,  "passwordPref is " + passwordPref);
        Log.d(TAG,  "idPref is " + idPref);


        queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WEBSERVICE_API + "/rest/v1/login?email=" + usernamePref + "&wachtwoord=" + passwordPref, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        // display response on Success
                        Log.d("Response", response.toString());
                        Log.d(TAG, "entered respone");
                        JSONObject jsono = (JSONObject) response;

                        // Log the output as debug information
                        Log.d(TAG, jsono.toString());

                        // Convert REST String to Pojo's using GSON libraries
                        Persoon persoon = new Gson().fromJson(jsono.toString(), Persoon.class);
                        Log.d("Person to string: ", persoon.toString());
                        System.out.println(persoon.getApi());

                        Log.d(TAG, "Preference has been changed");
                        usernamePref = preferences.getString("UsernamePref", "Undefined");
                        passwordPref = preferences.getString("PasswordPref", "Undefined");
                        idPref = preferences.getLong("IdPref", -1);

                        // Switching activity and checking if there has been logged in before
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        //maybe add some user feedback
                        if (usernameField.getText().length() != 0) {
                            if (error.toString().equals("com.android.volley.AuthFailureError")) {
                                usernameField.setText("");
                                usernameField.setHint(getString(R.string.login_field_username_error_changed));
                                usernameField.setError(getString(R.string.login_field_username_error_changed));

                                passwordField.setText("");
                                passwordField.setHint(getString(R.string.login_field_password_error_changed));
                                passwordField.setError(getString(R.string.login_field_password_error_changed));
                            }
                        }

                    }
                }
        );
        queue.start();
        queue.add(req);



//        if (!usernamePref.equals("Undefined")) {
//            Intent intent = new Intent(LoginActivity.this, PlayerDetailsActivity.class);
//            startActivity(intent);
//        }

        usernameField = findViewById(R.id.fldName);
        passwordField = findViewById(R.id.fldPassword);
        loginButton = findViewById(R.id.btnLogin);




//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "Button has been clicked");
//
//                // Check for password as well later.
//                if (usernameField.getText().toString().equals(""))
//                {
//                    usernameField.setHint(getString(R.string.login_field_username_error));
//                    usernameField.setError(getString(R.string.login_field_username_error));
//                }else if(passwordField.getText().toString().equals(""))
//                {
//                    passwordField.setHint(getString(R.string.login_field_username_error));
//                    passwordField.setError(getString(R.string.login_field_username_error));
//                }else
//                {
//
//                    queue = Volley.newRequestQueue(this);
//
//                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WEBSERVICE_API + "/rest/v1/login", null, this, this);
//
//                    SharedPreferences.Editor editor = preferences.edit();
//                    // move this to the login click handler later
//                    editor.putString("UsernamePref", usernameField.getText().toString());
//                    editor.apply();
//                    Log.d(TAG, "Preference has been changed");
//                    hasLoggedIn = preferences.getString("UsernamePref", "Undefined");
//
//                    // Switching activity and checking if there has been logged in before
//                    if (!hasLoggedIn.equals("Undefined"))
//                    {
//                        Log.d(TAG, "hasLoggedIn in if statement is " + hasLoggedIn);
//                        Log.d(TAG, "usernameString in if statement is " + usernameField.getText().toString());
//                        Log.d(TAG, "UsernamePref has been entered");
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
    }
    public void onBtnLoginClick(View view){
        Log.d(TAG, "Button has been clicked");

        usernameString = usernameField.getText().toString();
        passwordString = passwordField.getText().toString();

        // Check for password as well later.
        if (usernameString.equals(""))
        {
            usernameField.setHint(getString(R.string.login_field_username_error));
            usernameField.setError(getString(R.string.login_field_username_error));
        }else if(passwordString.equals(""))
        {
            passwordField.setHint(getString(R.string.login_field_password_error));
            passwordField.setError(getString(R.string.login_field_password_error));
        }else
        {

            queue = Volley.newRequestQueue(this);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WEBSERVICE_API + "/rest/v1/login?email=" + usernameString + "&wachtwoord=" + passwordString, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            // display response on Success
                            Log.d("Response", response.toString());
                            Log.d(TAG, "entered respone");
                            JSONObject jsono = (JSONObject) response;

                            // Log the output as debug information
                            Log.d(TAG, jsono.toString());

                            // Convert REST String to Pojo's using GSON libraries
                            Persoon persoon = new Gson().fromJson(jsono.toString(), Persoon.class);
                            Log.d("Person to string: ", persoon.toString());
                            System.out.println(persoon.getApi());

                            SharedPreferences.Editor editor = preferences.edit();
                            // move this to the login click handler later
                            editor.putString("UsernamePref", usernameString);
                            editor.putString("PasswordPref", passwordString);
                            editor.putString("ApiPref", persoon.getApi());
                            editor.putLong("IdPref", persoon.getId());
                            editor.apply();
                            Log.d(TAG, "Preference has been changed");
                            usernamePref = preferences.getString("UsernamePref", "Undefined");
                            passwordPref = preferences.getString("PasswordPref", "Undefined");
                            passwordPref = preferences.getString("ApiPref", "Undefined");
                            idPref = preferences.getLong("IdPref", -1);


                            // Switching activity and checking if there has been logged in before
                            if (!usernamePref.equals("Undefined"))
                            {
                                Log.d(TAG, "usernamePref in if statement is " + usernamePref);
                                Log.d(TAG, "usernameString in if statement is " + usernameString);
                                Log.d(TAG, "passwordPref in if statement is " + passwordPref);
                                Log.d(TAG, "passwordString in if statement is " + passwordString);
                                Log.d(TAG, "ApiPref in if statement is " + apiPref);
                                Log.d(TAG, "idPref in if statement is " + idPref);
                                Log.d(TAG, "UsernamePref has been entered");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                            //maybe add some user feedback
                            if (error.toString().equals("com.android.volley.AuthFailureError")){
                                usernameField.setText("");
                                usernameField.setHint(getString(R.string.login_field_username_error_wrong));
                                usernameField.setError(getString(R.string.login_field_username_error_wrong));

                                passwordField.setText("");
                                passwordField.setHint(getString(R.string.login_field_password_error_wrong));
                                passwordField.setError(getString(R.string.login_field_password_error_wrong));
                            }

                        }
                    }
            );
            queue.start();
            queue.add(req);

        }
    }
}
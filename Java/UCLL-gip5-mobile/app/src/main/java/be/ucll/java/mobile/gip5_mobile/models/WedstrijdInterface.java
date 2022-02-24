package be.ucll.java.mobile.gip5_mobile.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WedstrijdInterface {
        @GET("/rest/v1/wedstrijdMetPloegen")
        Call<List<Wedstrijd>> getWedstrijden();
}

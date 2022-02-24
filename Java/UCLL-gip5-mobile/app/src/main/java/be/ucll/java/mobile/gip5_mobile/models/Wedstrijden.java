package be.ucll.java.mobile.gip5_mobile.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Wedstrijden {
    @Expose
    private List<Wedstrijd> wedstrijden;

    public Wedstrijden(List<Wedstrijd> wedstrijden) {
        this.wedstrijden = wedstrijden;
    }

    public List<Wedstrijd> getWedstrijden() {
        return wedstrijden;
    }

    public void setWedstrijden(List<Wedstrijd> wedstrijden) {
        this.wedstrijden = wedstrijden;
    }
}

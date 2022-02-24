package be.ucll.java.mobile.gip5_mobile.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

import java.time.LocalDateTime;
public class Wedstrijd {
    @Expose
    private Long id;
    @Expose
    private String tijdstip;
    @Expose
    private String locatie;
    @Expose
    private Long thuisploegId;
    @Expose
    private Long tegenstanderId;
    @Expose
    private String tegenstander;
    @Expose
    private String thuisploeg;

    public Wedstrijd(Long id,String tijdstip, String locatie, Long thuisploegId, Long tegenstanderId, String tegenstander, String thuisploeg) {
        this.id = id;
        this.tijdstip = tijdstip;
        this.locatie = locatie;
        this.thuisploegId = thuisploegId;
        this.tegenstanderId = tegenstanderId;
        this.tegenstander = tegenstander;
        this.thuisploeg = thuisploeg;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Long getThuisploegId() {
        return thuisploegId;
    }

    public void setThuisploegId(Long thuisploegId) {
        this.thuisploegId = thuisploegId;
    }

    public Long getTegenstanderId() {
        return tegenstanderId;
    }

    public void setTegenstanderId(Long tegenstanderId) {
        this.tegenstanderId = tegenstanderId;
    }

    public String getTegenstander() {
        return tegenstander;
    }

    public void setTegenstander(String tegenstander) {
        this.tegenstander = tegenstander;
    }

    public String getThuisploeg() {
        return thuisploeg;
    }

    public void setThuisploeg(String thuisploeg) {
        this.thuisploeg = thuisploeg;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

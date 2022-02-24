package be.ucll.java.gip5.model;

import javax.persistence.*;
import java.util.Date;

import java.time.LocalDateTime;
@Entity
@Table(name ="wedstrijd", schema = "gip5")
public class Wedstrijd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "dseq", sequenceName = "wedstrijd_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name="tijdstip")
    private LocalDateTime tijdstip;
    @Column(name="locatie")
    private String locatie;
    @Column(name="thuisPloeg")
    private Long thuisPloeg;
    @Column(name="tegenstander")
    private Long tegenstander;

    private Wedstrijd(WedstrijdBuilder builder){
        setId(builder.id);
        setLocatie(builder.locatie);
        setTegenstander(builder.tegenstander);
        setThuisPloeg(builder.thuisPloeg);
        setTijdstip(builder.tijdstip);
    }
    public Wedstrijd(){}

    public static final class WedstrijdBuilder {
        private Long id;
        private LocalDateTime tijdstip;
        private String locatie;
        private Long thuisPloeg;
        private Long tegenstander;

        public WedstrijdBuilder() {
        }

        public static WedstrijdBuilder Wedstrijd() {
            return new WedstrijdBuilder();
        }

        public WedstrijdBuilder(Wedstrijd copy) {
            this.id = copy.id;
            this.tijdstip = copy.tijdstip;
            this.locatie = copy.locatie;
            this.thuisPloeg = copy.thuisPloeg;
            this.tegenstander = copy.tegenstander;
        }

        public WedstrijdBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WedstrijdBuilder tijdstip(LocalDateTime tijdstip) {
            this.tijdstip = tijdstip;
            return this;
        }

        public WedstrijdBuilder locatie(String locatie) {
            this.locatie = locatie;
            return this;
        }

        public WedstrijdBuilder thuisPloeg(Long thuisPloeg) {
            this.thuisPloeg = thuisPloeg;
            return this;
        }

        public WedstrijdBuilder tegenstander(Long tegenstander) {
            this.tegenstander = tegenstander;
            return this;
        }

        public Wedstrijd build() {
            return new Wedstrijd(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(LocalDateTime tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Long getThuisPloeg() {
        return thuisPloeg;
    }

    public void setThuisPloeg(Long thuisPloeg) {
        this.thuisPloeg = thuisPloeg;
    }

    public Long getTegenstander() {
        return tegenstander;
    }

    public void setTegenstander(Long tegenstander) {
        this.tegenstander = tegenstander;
    }


}

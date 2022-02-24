package be.ucll.java.mobile.gip5_mobile.models;


public class Toewijzing {
    private Long id;

    private Long persoonId;

    private Rol rol;

    private Long ploegId;

    private Toewijzing(ToewijzingBuilder builder){
        setId(builder.id);
        setPloegId(builder.ploegId);
        setRol(builder.rol);
        setPersoonId(builder.persoonId);
    }
    public Toewijzing(){}

    public static final class ToewijzingBuilder{
        private Long id;
        private Long persoonId;
        private Rol rol;
        private Long ploegId;

        public ToewijzingBuilder(){}
        public static ToewijzingBuilder Deelname(){return new ToewijzingBuilder();}
        public ToewijzingBuilder(Toewijzing copy){
            this.id = copy.id;
            this.persoonId = copy.persoonId;
            this.rol = copy.rol;
            this.ploegId = copy.ploegId;
        }
        public ToewijzingBuilder id(Long id){
            this.id = id;
            return this;
        }
        public ToewijzingBuilder persoonId(Long persoonId){
            this.persoonId = persoonId;
            return this;
        }
        public ToewijzingBuilder rol(Rol rol){
            this.rol = rol;
            return this;
        }
        public ToewijzingBuilder ploegId(Long ploegId){
            this.ploegId = ploegId;
            return this;
        }
        public Toewijzing build(){return new Toewijzing(this);}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersoonId() {
        return persoonId;
    }

    public void setPersoonId(Long persoonId) {
        this.persoonId = persoonId;
    }

    public Rol getRolId() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getPloegId() {
        return ploegId;
    }

    public void setPloegId(Long ploegId) {
        this.ploegId = ploegId;
    }
}

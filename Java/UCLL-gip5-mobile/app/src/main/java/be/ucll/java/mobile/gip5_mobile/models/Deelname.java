package be.ucll.java.mobile.gip5_mobile.models;


public class Deelname {

    private Long id;

    private Long persoonId;

    private Long wedstrijdId;

    private String commentaar;

    private Status status;

    private Deelname(DeelnameBuilder builder) {
        setCommentaar(builder.commentaar);
        setPersoonId(builder.persoonId);
        setId(builder.id);
        setWedstrijdId(builder.wedstrijdId);
        setStatus(builder.status);
    }

    public Deelname() {
    }

    public static final class DeelnameBuilder {

        private Long id;
        private Long persoonId;
        private Long wedstrijdId;
        private String commentaar;
        private Status status;
        public DeelnameBuilder(){
        }

        public static DeelnameBuilder Deelname() {
            return new DeelnameBuilder();
        }

        public DeelnameBuilder(Deelname copy) {
            this.id = copy.id;
            this.persoonId = copy.id;
            this.wedstrijdId = copy.wedstrijdId;
            this.commentaar = copy.commentaar;
            this.status = copy.status;
        }

        public DeelnameBuilder id(Long id){
            this.id = id;
            return this;
        }
        public DeelnameBuilder persoonId(Long persoonId){
            this.persoonId = persoonId;
            return this;
        }
        public DeelnameBuilder wedstrijdId(Long wedstrijdId){
            this.wedstrijdId = wedstrijdId;
            return this;
        }
        public DeelnameBuilder commentaar(String commentaar){
            this.commentaar = commentaar;
            return this;
        }
        public DeelnameBuilder status(Status status){
            this.status = status;
            return this;
        }
        public Deelname build(){
            return new Deelname(this);
        }
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getPersoonId() {
        return persoonId;
    }

    public void setPersoonId(Long persoonId) {
        this.persoonId = persoonId;
    }

    public Long getWedstrijdId() {
        return wedstrijdId;
    }

    public void setWedstrijdId(Long wedstrijdId) {
        this.wedstrijdId = wedstrijdId;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

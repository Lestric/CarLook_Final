package carlook.services.util;


import carlook.objects.dto.Kundedto;

public class KundeBuilder {

    private static KundeBuilder builder = null;
    private Kundedto kundedto = null;

    public KundeBuilder createNewUser(){
        this.kundedto = new Kundedto();
        return builder;
    }
    public static KundeBuilder getInstance(){
        builder = new KundeBuilder();
        return builder;
    }

    public KundeBuilder withEmail(String email){
        this.kundedto.setEmail(email);
        return this;
    }

    public KundeBuilder withPw(String pw){
        this.kundedto.setPasswort(pw);
        return this;
    }

    public KundeBuilder with2ndPw(String pw){
        this.kundedto.setPasswort2(pw);
        return this;
    }

    public KundeBuilder withVorname(String vn){
        this.kundedto.setVorname(vn);
        return this;
    }

    public KundeBuilder withNachname(String Nn){
        this.kundedto.setNachname(Nn);
        return this;
    }

    public Kundedto getKundedto(){
        return this.kundedto;
    }
}
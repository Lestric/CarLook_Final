package carlook.services.util;


import carlook.objects.dto.Kunde;

public class KundeBuilder {

    private static KundeBuilder builder = null;
    private Kunde kunde = null;

    public KundeBuilder createNewUser(){
        this.kunde = new Kunde();
        return builder;
    }
    public static KundeBuilder getInstance(){
        builder = new KundeBuilder();
        return builder;
    }

    public KundeBuilder withEmail(String email){
        this.kunde.setEmail(email);
        return this;
    }

    public KundeBuilder withPw(String pw){
        this.kunde.setPasswort(pw);
        return this;
    }

    public KundeBuilder with2ndPw(String pw){
        this.kunde.setPasswort2(pw);
        return this;
    }

    public KundeBuilder withVorname(String vn){
        this.kunde.setVorname(vn);
        return this;
    }

    public KundeBuilder withNachname(String Nn){
        this.kunde.setNachname(Nn);
        return this;
    }

    public Kunde getKunde(){
        return this.kunde;
    }
}
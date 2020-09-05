package carlook.objects.entities;

public class Auto {

    private int auto_id;
    private String marke;
    private String baujahr;
    private String modell;
    private String zustand;
    private String beschreibung;
    private int preis;

    // getter und setter

    public int getAuto_id() {
        return auto_id;
    }

    public String getMarke() {
        return marke;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public String getModell() {
        return modell;
    }

    public String getZustand() {
        return zustand;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public int getPreis() {
        return preis;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public void setZustand(String zustand) {
        this.zustand = zustand;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

}

package carlook.objects.dto;


public class Kunde extends User {

    private static String vorname;
    private static String nachname;
    private static int kundeId;


    public static String getVorname() {
        return Kunde.vorname;
    }

    public static void setVorname(String vorname) {
        Kunde.vorname = vorname;
    }

    public static String getNachname() {
        return Kunde.nachname;
    }

    public static void setNachname(String nachname) {
        Kunde.nachname = nachname;
    }

    public static int getKundeId() {
        return Kunde.kundeId;
    }

    public static void setKundeId(int kundeId) {
        Kunde.kundeId = kundeId;
    }
}

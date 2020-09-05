package carlook.objects.dto;


public class Kundedto extends User {

    private static String vorname;
    private static String nachname;
    private static int kundeId;


    public static String getVorname() {
        return Kundedto.vorname;
    }

    public static void setVorname(String vorname) {
        Kundedto.vorname = vorname;
    }

    public static String getNachname() {
        return Kundedto.nachname;
    }

    public static void setNachname(String nachname) {
        Kundedto.nachname = nachname;
    }

    public static int getKundeId() {
        return Kundedto.kundeId;
    }

    public static void setKundeId(int kundeId) {
        Kundedto.kundeId = kundeId;
    }
}

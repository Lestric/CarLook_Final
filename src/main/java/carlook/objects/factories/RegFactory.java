package carlook.objects.factories;

import carlook.objects.dto.Kunde;


public class RegFactory {

    private RegFactory(){

    }

    public static Kunde createKundeReg(Kunde request){
        Kunde kunde = new Kunde();
        kunde.setEmail(request.getEmail());
        kunde.setPasswort(request.getPasswort());
        kunde.setPasswort2(request.getPasswort2());
        kunde.setRole(request.getRole());
        kunde.setVorname(request.getVorname());
        kunde.setNachname(request.getNachname());

        return kunde;
    }

}

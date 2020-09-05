package carlook.objects.factories;

import carlook.objects.dto.Kundedto;


public class RegFactory {

    private RegFactory(){

    }

    public static Kundedto createKundeReg(Kundedto request){
        Kundedto kundedto = new Kundedto();
        kundedto.setEmail(request.getEmail());
        kundedto.setPasswort(request.getPasswort());
        kundedto.setPasswort2(request.getPasswort2());
        kundedto.setRole(request.getRole());
        kundedto.setVorname(request.getVorname());
        kundedto.setNachname(request.getNachname());

        return kundedto;
    }

}

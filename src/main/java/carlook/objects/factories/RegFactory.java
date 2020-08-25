package carlook.objects.factories;

import carlook.objects.dto.User;
import carlook.objects.entities.Registrierung;

public class RegFactory {

    private RegFactory(){

    }

    public static Registrierung createKundeReg(User request){
        Registrierung reg = new Registrierung();
        reg.setEmail(request.getEmail());
        reg.setPasswort(request.getPasswort());

        return reg;
    }
}

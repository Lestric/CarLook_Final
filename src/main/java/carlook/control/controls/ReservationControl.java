package carlook.control.controls;

import carlook.objects.dao.UserSearchReservDAO;
import carlook.objects.dto.Autodto;
import carlook.objects.dto.Kundedto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationControl {

    private int kunden_id;
    private int auto_id;

    //Singleton

    private static carlook.control.controls.ReservationControl process = null;

    private ReservationControl(){

    }

    public static carlook.control.controls.ReservationControl getInstance(){
        if(process == null){
            process = new carlook.control.controls.ReservationControl();
        }
        return process;
    }

    private void setzeKunden_id(){
        this.kunden_id = Kundedto.getKundeId();
    }

    public void createReservation(int auto_id){
        this.auto_id = auto_id;
        setzeKunden_id();


        //Dao aufrufen und beide ids übergeben für db eintrag

        UserSearchReservDAO.getInstance().createReservation(this.auto_id, this.kunden_id);

    }

    public List<Autodto> getAllReservations() throws SQLException {

        List<Integer> autoIds = UserSearchReservDAO.getInstance().getAllReservationForKunde(Kundedto.getKundeId());

        List<Autodto> retListAutodtos = new ArrayList<>();

        for(Integer id : autoIds){

            UserSearchReservDAO.getInstance().searchAutos(id, retListAutodtos);

        }

        return retListAutodtos;
    }

}

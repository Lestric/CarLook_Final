package carlook.control.controls;

import carlook.objects.dao.ProfilDAO;
import carlook.objects.dto.Auto;
import carlook.objects.dto.Kunde;

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
        this.kunden_id = Kunde.getKundeId();
    }

    public void createReservation(int auto_id){
        this.auto_id = auto_id;
        setzeKunden_id();


        //Dao aufrufen und beide ids übergeben für db eintrag

        ProfilDAO.getInstance().createReservation(this.auto_id, this.kunden_id);

    }

    public List<Auto> getAllReservations() throws SQLException {

        List<Integer> autoIds = ProfilDAO.getInstance().getAllReservationForKunde(Kunde.getKundeId());

        List<Auto> retListAutos = new ArrayList<>();

        for(Integer id : autoIds){

            ProfilDAO.getInstance().searchAutos(id, retListAutos);

        }

        return retListAutos;
    }

}

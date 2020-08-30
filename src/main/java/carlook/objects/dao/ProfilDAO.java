package carlook.objects.dao;


import carlook.objects.dto.Auto;
import carlook.objects.dto.Kunde;
import carlook.services.util.HashFunktionsKlasse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProfilDAO extends AbstractDAO {



    private static ProfilDAO dao = null;
    private  ProfilDAO(){

    }
    public static ProfilDAO getInstance(){
        if(dao == null){
            dao = new ProfilDAO();
        }
        return dao;
    }

    private static final String WHEREID = "WHERE benutzerid = '";


    public void deleteUser(int id){

        String sql2 = " DELETE FROM carlook.benutzer WHERE benutzer_id = '" + id + "';";

        try {
            PreparedStatement statement2 = this.getPreparedStatement(sql2);
            statement2.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = " DELETE FROM carlook.kunde WHERE kunde_id = '" + id + "';";

        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void registerKunde(Kunde kunde){

        // erzeuge Kunden in Kunden Tabelle

        String sql = "insert into carlook.kunde (vorname, nachname) values (?,?)";
        PreparedStatement statement = this.getPreparedStatement(sql);

        try{
            statement.setString(1, kunde.getVorname());
            statement.setString(2, kunde.getNachname());

            statement.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        // frage kundenID für fremdschlüssel und zum speichern in Kunde dto ab

        String sql_getID = "SELECT kunde_id FROM carlook.kunde ORDER BY kunde_id DESC LIMIT 1";
        PreparedStatement statement2 = this.getPreparedStatement(sql_getID);

        try(ResultSet set = statement2.executeQuery()){

            if(set.next()){
                kunde.setId(set.getInt(1));
                kunde.setKundeId(set.getInt(1));
            }

        } catch(SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        // erzeuge User Tabelleneintrag mit kundeID = userID und fremdschlüssel und allen Attributen eines Kunden

        String sql3 = "insert into carlook.benutzer (benutzer_id, email, passwort, kunde_id, role) values (?,?,?,?,?)";
        PreparedStatement statement3 = this.getPreparedStatement(sql3);

        try{
            statement3.setInt(1, kunde.getId());
            statement3.setString(2, kunde.getEmail());
            statement3.setString(3, HashFunktionsKlasse.getHash(kunde.getPasswort().getBytes(), "MD5"));
            statement3.setInt(4, kunde.getKundeId());
            statement3.setString(5, kunde.getRole());

            statement3.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Auto> searchAutos(String marke, String modell) throws SQLException {

        marke = marke.toUpperCase();
        modell = modell.toUpperCase();
        String basic = "SELECT * FROM carlook.auto";
        String sql;
        PreparedStatement statement;

        if (marke.equals("")) {
            if (modell.equals("")) {

                // beide leer
                sql = basic;
                statement = this.getPreparedStatement(sql);

            } else {

                //marke leer
                sql = basic + " WHERE UPPER(modell) LIKE ?";
                statement = this.getPreparedStatement(sql);
                statement.setString(1, "%"+ modell + "%");

            }
        }else {
            if(modell.equals("")){

                //modell leer
                sql = basic + " WHERE UPPER(marke) LIKE ?";
                statement = this.getPreparedStatement(sql);
                statement.setString(1, "%" + marke + "%");

            }else{

                // beide voll
                sql = basic + " WHERE UPPER(marke) LIKE ? AND UPPER(modell) LIKE ?";
                statement = this.getPreparedStatement(sql);
                statement.setString(1, "%" + marke + "%");
                statement.setString(2, "%" + modell + "%");
            }
        }

        List<Auto> retListAuto = new ArrayList<>();

        ResultSet rs = statement.executeQuery();

        while(rs.next()){

            Auto auto = new Auto();
            auto.setAuto_id(rs.getInt(1));
            auto.setMarke(rs.getString(2));
            auto.setBaujahr(rs.getString(3));
            auto.setModell(rs.getString(4));
            auto.setZustand(rs.getString(5));
            auto.setBeschreibung(rs.getString(6));
            auto.setPreis(rs.getInt(7));
            retListAuto.add(auto);

        }


        return retListAuto;
    }

}

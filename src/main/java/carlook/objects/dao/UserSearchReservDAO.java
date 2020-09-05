package carlook.objects.dao;


import carlook.objects.dto.Autodto;
import carlook.objects.dto.Kundedto;
import carlook.services.util.HashFunktionsKlasse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserSearchReservDAO extends AbstractDAO {



    private static UserSearchReservDAO dao = null;
    private UserSearchReservDAO(){

    }
    public static UserSearchReservDAO getInstance(){
        if(dao == null){
            dao = new UserSearchReservDAO();
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
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = " DELETE FROM carlook.kunde WHERE kunde_id = '" + id + "';";

        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            statement.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void registerKunde(Kundedto kundedto){

        // erzeuge Kunden in Kunden Tabelle

        String sql = "insert into carlook.kunde (vorname, nachname) values (?,?)";
        PreparedStatement statement = this.getPreparedStatement(sql);

        try{
            statement.setString(1, kundedto.getVorname());
            statement.setString(2, kundedto.getNachname());

            statement.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        // frage kundenID für fremdschlüssel und zum speichern in Kunde dto ab

        String sql_getID = "SELECT kunde_id FROM carlook.kunde ORDER BY kunde_id DESC LIMIT 1";
        PreparedStatement statement2 = this.getPreparedStatement(sql_getID);

        try(ResultSet set = statement2.executeQuery()){

            if(set.next()){
                kundedto.setId(set.getInt(1));
                kundedto.setKundeId(set.getInt(1));
            }

        } catch(SQLException ex){
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        // erzeuge User Tabelleneintrag mit kundeID = userID und fremdschlüssel und allen Attributen eines Kunden

        String sql3 = "insert into carlook.benutzer (benutzer_id, email, passwort, kunde_id, role) values (?,?,?,?,?)";
        PreparedStatement statement3 = this.getPreparedStatement(sql3);

        try{
            statement3.setInt(1, kundedto.getId());
            statement3.setString(2, kundedto.getEmail());
            statement3.setString(3, HashFunktionsKlasse.getHash(kundedto.getPasswort().getBytes(), "MD5"));
            statement3.setInt(4, kundedto.getKundeId());
            statement3.setString(5, kundedto.getRole());

            statement3.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Autodto> searchAutos(String marke, String modell) throws SQLException {

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

        List<Autodto> retListAutodto = new ArrayList<>();

        ResultSet rs = statement.executeQuery();

        while(rs.next()){

            Autodto autodto = new Autodto();
            autodto.setAuto_id(rs.getInt(1));
            autodto.setMarke(rs.getString(2));
            autodto.setBaujahr(rs.getString(3));
            autodto.setModell(rs.getString(4));
            autodto.setZustand(rs.getString(5));
            autodto.setBeschreibung(rs.getString(6));
            autodto.setPreis(rs.getInt(7));
            retListAutodto.add(autodto);

        }


        return retListAutodto;
    }

    public List<Autodto> searchAutos(Integer autoID, List<Autodto> retListAutodto) throws SQLException {

        String sql;
        PreparedStatement statement;

        sql = "SELECT * FROM carlook.auto" + " WHERE auto_id = ? ";
        statement = this.getPreparedStatement(sql);
        statement.setInt(1,   autoID );

        ResultSet rs = statement.executeQuery();

        while(rs.next()){

            Autodto autodto = new Autodto();
            autodto.setAuto_id(rs.getInt(1));
            autodto.setMarke(rs.getString(2));
            autodto.setBaujahr(rs.getString(3));
            autodto.setModell(rs.getString(4));
            autodto.setZustand(rs.getString(5));
            autodto.setBeschreibung(rs.getString(6));
            autodto.setPreis(rs.getInt(7));
            retListAutodto.add(autodto);

        }


        return retListAutodto;

    }

    public void createReservation(int autoID, int kundeID){

        // erzeuge neue Zeile mit beiden IDs

        String sql = "insert into carlook.benutzer_reserviert_auto (kunde_id, auto_id) values (?,?) ON CONFLICT (kunde_id, auto_id) DO NOTHING;";
        PreparedStatement statement = this.getPreparedStatement(sql);

        try{
            statement.setInt(1, kundeID);
            statement.setInt(2, autoID);

            statement.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(UserSearchReservDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Integer> getAllReservationForKunde(int kundeID) throws  SQLException{

        String sql;
        PreparedStatement statement;

        sql = "SELECT * FROM carlook.benutzer_reserviert_auto" + " WHERE kunde_id = ? ";
        statement = this.getPreparedStatement(sql);
        statement.setInt(1, kundeID);

        ResultSet rs = statement.executeQuery();

        List<Integer> autoIdList = new ArrayList<>();

        while(rs.next()){
            autoIdList.add(rs.getInt(2));
        }


        return autoIdList;

    }

}

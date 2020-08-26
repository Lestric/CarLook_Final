package carlook.objects.dao;


import carlook.objects.dto.Kunde;
import carlook.objects.dto.User;
import carlook.services.util.HashFunktionsKlasse;
import carlook.services.util.Roles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public void deleteUser(User user){
        String sql = "";
        if(user.getRole().equals(Roles.KUNDE)){
            sql = "DELETE FROM carlook.benutzer WHERE benutzerid = '" + user.getId() + "' AND "
            + " DELETE FROM carlook.kunde WHERE kunde_id = '" + user.getId() + "';";
        }
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

    /*

    public PreparedStatement getStudentStatement(String name, String ort, String studiengang) throws SQLException {
        name = name.toUpperCase();
        ort = ort.toUpperCase();
        studiengang = studiengang.toUpperCase();
        String basic = "SELECT * FROM carlook.kunde";
        PreparedStatement statement;
        String sql;

        if (name.equals("")) {
            if (ort.equals("")) {
                if (studiengang.equals("")) {
                    statement = this.getPreparedStatement(basic);
                    return statement;
                }
                sql = basic + " WHERE UPPER(student_studiengang) LIKE ?";
                statement = this.getPreparedStatement(sql);
                statement.setString(1, "%"+ studiengang + "%");
                return statement;
            }
            else {
                if (studiengang.equals("")) {
                    sql = basic + " WHERE UPPER(adresse_ort) = ?";
                    statement = this.getPreparedStatement(sql);
                    statement.setString(1, ort);
                    return statement;
                }
            }
            sql = basic + " WHERE UPPER(adresse_ort) = ? AND UPPER(student_studiengang) LIKE ?";
            statement = this.getPreparedStatement(sql);
            statement.setString(1, ort);
            statement.setString(2, "%" + studiengang + "%");
            return statement;
        }
        if (ort.equals("")) {
            if (studiengang.equals("")) {
                sql = basic + " WHERE UPPER(student_nachname) LIKE ?";
                statement = this.getPreparedStatement(sql);
                statement.setString(1, "%" + name + "%");
                return statement;
            }
            sql = basic + " WHERE UPPER(student_nachname) LIKE ? AND UPPER(student_studiengang) LIKE ?";
            statement = this.getPreparedStatement(sql);
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + studiengang + "%");
            return statement;
        }
        if(studiengang.equals("")){
            sql = basic + " WHERE UPPER(student_nachname) LIKE ? AND UPPER(adresse_ort) = ?";
            statement = this.getPreparedStatement(sql);
            statement.setString(1, "%" + name + "%");
            statement.setString(2, ort);
        }
        else{
            sql = basic + " WHERE UPPER(student_nachname) LIKE ? AND UPPER(adresse_ort) = ? AND UPPER(student_studiengang) LIKE ?";
            statement = this.getPreparedStatement(sql);
            statement.setString(1, "%" + name + "%");
            statement.setString(2, ort);
            statement.setString(3, "%" + studiengang + "%");
        }
        return statement;
    }

     */


}

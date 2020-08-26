package carlook.objects.dao;


import carlook.objects.dto.Kunde;
import carlook.objects.dto.User;
import carlook.objects.entities.Registrierung;
import carlook.services.util.HashFunktionsKlasse;
import carlook.services.util.Roles;

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

    public void updateKundeProfile(Kunde kunde){
        String sql = "UPDATE coll_at_hbrs.student_view SET "
                + "email = '" + kunde.getEmail() + "', "
                + "student_vorname = '" + kunde.getVorname() + "', "
                + "student_nachname = '" + kunde.getNachname() + "', "
                + "kunde_id = '" + kunde.getKundeId() + "';";

        try(PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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

    public void registerKunde(Registrierung reg){
        String sql = "insert into carlook.benutzer (email, passwort) values (?,?)";
        PreparedStatement statement = this.getPreparedStatement(sql);

        //Angaben in student_regsitrierung VIEW schreiben
        try{
            statement.setString(1, reg.getEmail());
            statement.setString(2, HashFunktionsKlasse.getHash(reg.getPasswort().getBytes(), "MD5"));

            statement.executeUpdate();

        } catch(SQLException ex){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Kunde> searchStudent(String name, String ort, String studiengang) throws SQLException { // zu search auto aendern
        List<Kunde> kundeList = new ArrayList<>();
        PreparedStatement statement = getStudentStatement(name, ort, studiengang);

        try(ResultSet rs = statement.executeQuery()){
            while(rs.next()){
                Kunde kunde = new Kunde();
                kunde.setVorname(rs.getString(6));
                kunde.setNachname(rs.getString(7));
                kunde.setId(rs.getInt(1));
                kundeList.add(kunde);
            }
        }catch(SQLException exception){
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return kundeList;
    }



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


}

package carlook.control.controls;

import carlook.objects.dto.Kundedto;
import carlook.objects.entities.Kunde;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import carlook.control.exceptions.DatabaseException;
import carlook.control.exceptions.NoSuchUserOrPassword;
import carlook.services.db.JDBCConnection;
import carlook.services.util.HashFunktionsKlasse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControl {

    private LoginControl(){

    }


    public static void checkAuthentication(String email, String passwort) throws NoSuchUserOrPassword, DatabaseException, SQLException {

        Kunde kunde = new Kunde();

        String sql = "SELECT * FROM carlook.benutzer WHERE carlook.benutzer.email = ? AND carlook.benutzer.passwort = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        statement.setString(1, email);
        statement.setString(2, HashFunktionsKlasse.getHash(passwort.getBytes(), "MD5") );

        try(ResultSet set = statement.executeQuery()){
            if(set.next()){
                Kundedto.setEmail(set.getString(2));
                Kundedto.setId(set.getInt(1));
                Kundedto.setKundeId(set.getInt(1));
                Kundedto.setPasswort(set.getString(3));
                kunde.setKundeId(set.getInt(1));
            }else{
                throw new NoSuchUserOrPassword();
            }
        } catch(SQLException ex){
            Logger.getLogger(carlook.control.controls.LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        VaadinSession.getCurrent().setAttribute("currentUser" , kunde);
    }

    public static void logoutUser() {
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation("#!Startseite");
        UI.getCurrent().getPage().reload();
    }
}

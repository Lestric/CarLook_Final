package carlook.control.controls;

import com.vaadin.ui.UI;
import carlook.control.exceptions.DatabaseException;
import carlook.control.exceptions.NoSuchUserOrPassword;
import carlook.objects.dto.User;
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
        User user = new User();

        String sql = "SELECT * FROM carlook.benutzer WHERE carlook.benutzer.email = ? AND carlook.benutzer.passwort = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        statement.setString(1, email);
        statement.setString(2, HashFunktionsKlasse.getHash(passwort.getBytes(), "MD5") );

        try(ResultSet set = statement.executeQuery()){
            if(set.next()){
                user.setEmail(set.getString(2));
                user.setId(set.getInt(1));
                user.setPasswort(set.getString(3));
            }else{
                throw new NoSuchUserOrPassword();
            }
        } catch(SQLException ex){
            Logger.getLogger(carlook.control.controls.LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void logoutUser() {
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation("#!Startseite");
        UI.getCurrent().getPage().reload();
    }
}

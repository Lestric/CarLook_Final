package carlook.control.controls;

import carlook.control.exceptions.DatabaseException;
import carlook.objects.dao.ProfilDAO;
import carlook.objects.dto.Kunde;
import carlook.objects.factories.RegFactory;
import carlook.services.db.JDBCConnection;
import carlook.services.util.RegistrationResult;
import carlook.services.util.Roles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationControl {

    private static carlook.control.controls.RegistrationControl process = null;

    private RegistrationControl(){

    }

    public static carlook.control.controls.RegistrationControl getInstance(){
        if(process == null){
            process = new carlook.control.controls.RegistrationControl();
        }
        return process;
    }

    public RegistrationResult createRegistration(Kunde request) throws SQLException, DatabaseException {
        RegistrationResult result = new RegistrationResult();
        result.setResult(true);
        Kunde kunde;
        String rolle = request.getRole();

        kunde = RegFactory.createKundeReg(request);


        if (!request.getPasswort2().equals(request.getPasswort())){
            result.setReason(RegistrationResult.FailureType.PASSWORDS_DONT_MATCH);
            result.setResult(false);
        }
        if ( request.getPasswort().length() < 8){
            result.setReason(RegistrationResult.FailureType.PASSWORD_TOO_SHORT);
            result.setResult(false);
        }
        if ( request.getPasswort() == null || request.getPasswort().equals("")) {
            result.setReason(RegistrationResult.FailureType.PASSWORD_MISSING);
            result.setResult(false);
        }

        //Check ob Email schon vorhanden ist
        String sql = "SELECT * FROM carlook.benutzer WHERE carlook.benutzer.email = ?";
        PreparedStatement statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        statement.setString(1, request.getEmail());
        try(ResultSet set = statement.executeQuery()){
            if(set.next()){
                result.setReason(RegistrationResult.FailureType.EMAIL_EXISTS);
                result.setResult(false);
            }
        }

        if (!RegistrationResult.isValid(request.getEmail())){
            result.setReason(RegistrationResult.FailureType.EMAIL_NOT_VALID);
            result.setResult(false);
        }

        if ( request.getEmail() == null || request.getEmail().equals("")) {
            result.setReason(RegistrationResult.FailureType.EMAIL_MISSING);
            result.setResult(false);
        }

        if(result.getResult()){
            if(rolle.equals(Roles.KUNDE)) {
                ProfilDAO.getInstance().registerKunde(kunde);
            }
        }

        return result;
    }
}

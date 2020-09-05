package carlook.objects.dao;

import carlook.control.exceptions.DatabaseException;
import carlook.services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO {

    protected PreparedStatement getPreparedStatement(String sql){
        PreparedStatement statement = null;
        try{
            statement = JDBCConnection.getInstance().getPreparedStatement(sql);
        } catch(DatabaseException ex){
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;

    }
}

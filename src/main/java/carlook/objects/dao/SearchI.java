package carlook.objects.dao;


import carlook.objects.dto.Kunde;

import java.sql.SQLException;
import java.util.List;

public interface SearchI {

    List<Kunde> searchStudent(String name, String ort, String studiengang) throws SQLException;

}

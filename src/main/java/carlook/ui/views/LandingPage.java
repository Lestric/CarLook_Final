package carlook.ui.views;

import carlook.control.controls.LoginControl;
import carlook.control.controls.ReservationControl;
import carlook.objects.dao.ProfilDAO;
import carlook.objects.dto.Auto;
import carlook.objects.dto.Kunde;
import carlook.services.util.Konstanten;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Title("Carlook")
@Theme("mytheme")

public class LandingPage extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {

            this.setUp();
    }

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        Panel panel = new Panel();

        panel.setSizeUndefined();
        this.addComponents(panel);
        this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        // 3 Buttons für Logout, User löschen und Auto Suche View

        Button btLogout = new Button("Logout", FontAwesome.SIGN_OUT);
        btLogout.setDescription("Hier können Sie ihr Profil ausloggen.");
        btLogout.addClickListener(e -> LoginControl.logoutUser());

        Button btloeschen = new Button("Profil löschen", FontAwesome.REMOVE);
        btloeschen.setDescription("Hier können Sie ihr Profil löschen.");
        btloeschen.addClickListener(e -> {
            ProfilDAO.getInstance().deleteUser(Kunde.getId());
            LoginControl.logoutUser();
        });

        Button btAutoView = new Button("Autos suchen", FontAwesome.CAR);
        btAutoView.setDescription("Hier können Sie Autos suchen.");
        btAutoView.addClickListener(e ->{
            UI.getCurrent().getNavigator().navigateTo( Konstanten.AUTOSUCHE );
        });

        Button btShowReservatedCars = new Button("Reservierte Autos", FontAwesome.CAR);
        btShowReservatedCars.setDescription("Hier finden Sie Ihre reservierten Autos.");
        btShowReservatedCars.addClickListener(e -> {

            Window windowResCars = new Window();

            windowResCars.center();
            windowResCars.setResizable(true);
            windowResCars.setDraggable(true);
            windowResCars.setClosable(true);
            windowResCars.setSizeUndefined();

            windowResCars.setContent(getWindowContentCars());

        });

        // content
        FormLayout content = new FormLayout();
        content.addComponent(new Label("<b><p style=\"font-size:40px ; color:#fcba03; text-shadow: 1px 1px 2px black;\">CarLook</p></b>", ContentMode.HTML));
        content.addComponents(btAutoView, btShowReservatedCars, btLogout, btloeschen);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

    }

    private VerticalLayout getWindowContentCars(){
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeUndefined();

        //Erstellung Tabelle mit Autos
        Grid<Auto> autoGrid = new Grid<>();
        autoGrid.setSizeUndefined();
        List<Auto> autoList = null;

        try {
            autoList = ReservationControl.getInstance().getAllReservations();
        } catch (SQLException ex) {
            Logger.getLogger(AutoSucheView.class.getName()).log(Level.SEVERE, null, ex);
        }

        autoGrid.removeAllColumns();
        autoGrid.setCaptionAsHtml(true);
        autoGrid.setCaption(" <span style='color:#fcba03; font-size:20px; text-shadow: 1px 1px 1px black '> " + "Reservierte Autos:" + " </span>");
        autoGrid.setItems(autoList);
        autoGrid.setHeightByRows(!autoList.isEmpty() ? autoList.size() : 1);

        autoGrid.addColumn(Auto::getMarke).setCaption("Marke");
        autoGrid.addColumn(Auto::getModell).setCaption("Modell");
        autoGrid.addColumn(Auto::getPreis).setCaption("Preis");
        autoGrid.addColumn(Auto::getBeschreibung).setCaption("Beschreibung");

        autoGrid.setWidth("70%");

        vl.addComponents(new Label("&nbsp" , ContentMode.HTML), autoGrid, new Label("&nbsp" , ContentMode.HTML));

        return vl;
    }

}
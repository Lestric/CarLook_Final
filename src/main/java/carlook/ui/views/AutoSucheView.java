package carlook.ui.views;

import carlook.control.controls.ReservationControl;
import carlook.objects.dao.UserSearchReservDAO;
import carlook.objects.dto.Auto;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Title("Carlook")
@Theme("mytheme")

public class AutoSucheView extends VerticalLayout implements View {

    Panel panel = new Panel();

    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.setUp();
    }

    public void setUp() {

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        Label lblMarke = new Label();
        lblMarke.setCaptionAsHtml(true);
        lblMarke.setCaption("<p style=\"font-size:22px ; color:#fcba03; text-shadow: 1px 1px 1px black;\">Marke: </p>");

        Label lblModell = new Label();
        lblModell.setCaptionAsHtml(true);
        lblModell.setCaption("<p style=\"font-size:22px ; color:#fcba03; text-shadow: 1px 1px 1px black;\">Modell: </p>");

        TextField tfMarke = new TextField();
        tfMarke.setDescription("Fügen Sie eine Marke für die Suche hinzu.");
        tfMarke.setValue("");

        TextField tfModell = new TextField();
        tfModell.setDescription("Fügen Sie ein Modell für die Suche hinzu.");
        tfModell.setValue("");

        // Arbeiten mit Valuechange Listener 

        tfMarke.addValueChangeListener(e -> {
            onTheFlySearch(tfMarke.getValue(), tfModell.getValue());
        });

        tfModell.addValueChangeListener(e -> {
            onTheFlySearch(tfMarke.getValue(), tfModell.getValue());
        });

        HorizontalLayout hzSuche = new HorizontalLayout();
        hzSuche.addComponents(lblMarke, tfMarke, new Label("&nbsp", ContentMode.HTML), lblModell, tfModell);
        hzSuche.setComponentAlignment(tfMarke, Alignment.MIDDLE_CENTER);
        hzSuche.setComponentAlignment(tfModell, Alignment.MIDDLE_CENTER);

        //Panel zur eingabe der Suchkriterien

        panel.setSizeUndefined();
        panel.setContent(hzSuche);
        panel.setWidth("50%");
        panel.setCaptionAsHtml(true);
        panel.setCaption("<b><p style=\"font-size:25px ; color:#fcba03; text-shadow: 1px 1px 1px black;\">Autosuche</p></b>");

        cleanView();

        onTheFlySearch(tfMarke.getValue(), tfModell.getValue());

    }

    private void cleanView(){

        this.removeAllComponents();
        this.addComponents(panel);
        this.setComponentAlignment(panel, Alignment.TOP_LEFT);
    }


    public void onTheFlySearch(String marke, String modell){

        cleanView();

        //Erstellung Tabelle mit Autos
        Grid<Auto> autoGrid = new Grid<>();
        autoGrid.setSizeUndefined();
        List<Auto> autoList = null;

        try {
            autoList = UserSearchReservDAO.getInstance().searchAutos(marke, modell);
        } catch (SQLException ex) {
            Logger.getLogger(AutoSucheView.class.getName()).log(Level.SEVERE, null, ex);
        }

        autoGrid.removeAllColumns();
        autoGrid.setCaptionAsHtml(true);
        autoGrid.setCaption(" <span style='color:#fcba03; font-size:20px; text-shadow: 1px 1px 1px black '> " + (marke.equals("") ? "Alle Autos:" : "Ergebnisse für: " + marke) + " </span>");
        autoGrid.setItems(autoList);
        autoGrid.setHeightByRows(!autoList.isEmpty() ? autoList.size() : 1);

        autoGrid.addColumn(Auto::getMarke).setCaption("Marke");
        autoGrid.addColumn(Auto::getModell).setCaption("Modell");
        autoGrid.addColumn(Auto::getPreis).setCaption("Preis");
        autoGrid.addColumn(Auto::getBaujahr).setCaption("Baujahr");
        autoGrid.addColumn(Auto::getZustand).setCaption("Zustand");
        autoGrid.addColumn(Auto::getBeschreibung).setCaption("Beschreibung");
        autoGrid.addColumn(Auto -> "Reservieren",
                new ButtonRenderer<>(clickEvent -> {

                    // Reservierung startet hier
                    reserviereAuto(clickEvent.getItem().getAuto_id());

                } ));

        this.addComponents(new Label("&nbsp" , ContentMode.HTML), autoGrid, new Label("&nbsp" , ContentMode.HTML));
        autoGrid.setWidth("80%");

    }

    public void reserviereAuto(int autoId){

        ReservationControl.getInstance().createReservation(autoId);

    }


}

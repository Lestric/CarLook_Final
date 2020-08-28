package carlook.ui.views;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import carlook.ui.components.MenuDropDown;

@Title("Carlook")
@Theme("mytheme")

public class AutoSucheView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.setUp();
    }

    public void setUp() {

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        MenuDropDown dropDown = new MenuDropDown();
        this.addComponent(dropDown);
        this.setComponentAlignment(dropDown, Alignment.TOP_RIGHT);

        //Panel zur eingabe der Suchkriterien

        Panel panel = new Panel();
        panel.setSizeUndefined();
        this.addComponents(panel);
        this.setComponentAlignment(panel, Alignment.TOP_CENTER);

        TextField tfMarke = new TextField();
        tfMarke.setDescription("Fügen Sie eine Marke für die Suche hinzu.");
        tfMarke.setValue("");
        tfMarke.setCaption("Marke:");

        TextField tfModell = new TextField();
        tfModell.setDescription("Fügen Sie ein Modell für die Suche hinzu.");
        tfModell.setValue("");
        tfMarke.setCaption("Modell:");

        // Erstellung Tabelle mit Autos



    }


}

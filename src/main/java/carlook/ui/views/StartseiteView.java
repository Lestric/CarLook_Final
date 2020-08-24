package carlook.ui.views;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import carlook.services.util.CSSKonstanten;
import carlook.services.util.Konstanten;
import carlook.ui.components.Footer;
import carlook.ui.components.Sidebar;

@Theme("mytheme")
@Title("CarLook-Startseite")

public class StartseiteView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setUp();
    }

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        //ist in mytheme festgelegt
        this.addStyleName(CSSKonstanten.STYLEANNIMATEDBACKGROUND);

        //Container für Sidebar & Content
        final HorizontalLayout mainFrame = new HorizontalLayout();
        mainFrame.addStyleName(CSSKonstanten.STYLESTARTMAINFRAME);


        Button buttonAnmelden = new Button("Login", FontAwesome.HOME);
        buttonAnmelden.setId("btnLogin");
        buttonAnmelden.addStyleName(CSSKonstanten.STYLESTARTBUTTON);
        buttonAnmelden.setDescription("Sind Sie bereits registriert? Dann loggen Sie sich hier ein.");
        buttonAnmelden.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(Konstanten.LOGIN));
        
        
        Button buttonRegistrieren = new Button("Registrieren", FontAwesome.USER);
        buttonRegistrieren.setId("btnRegistration");
        buttonRegistrieren.addStyleName(CSSKonstanten.STYLESTARTBUTTON);
        buttonRegistrieren.setDescription("Registrieren Sie sich hier.");
        buttonRegistrieren.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(Konstanten.REGISTER));


        //Sidebar erzeugen und Elemente hinzufügen
        Sidebar sidebar = new Sidebar();
        sidebar.addComponents(buttonAnmelden, buttonRegistrieren);

        //Baukasten zusammensetzen
        mainFrame.addComponents(sidebar, new Footer());
        mainFrame.setSizeFull();
        this.addComponent(mainFrame);

    }
}
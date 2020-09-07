package carlook.ui.views;


import carlook.objects.entities.Kunde;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import carlook.services.util.Konstanten;

@Theme("mytheme")
@Title("CarLook-Startseite")

public class StartseiteView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Kunde user =  (Kunde) VaadinSession.getCurrent().getAttribute("currentUser");

        //Wenn user schon eingeloggt, kommt er direkt auf Main statt auf Login
        if(user != null){
            UI.getCurrent().getNavigator().navigateTo(Konstanten.LANDINGPAGE);
        }else {
            this.setUp();
        }

    }

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        Label wilkommenText = new Label();
        wilkommenText.setCaptionAsHtml(true);
        wilkommenText.setCaption("<b><p style=\"font-size:60px ; color:#fcba03; text-shadow: 1px 1px 2px black;\">Willkommen bei CarLook</p></b>");

        Button buttonAnmelden = new Button("Login", FontAwesome.HOME);
        buttonAnmelden.setId("btnLogin");
        buttonAnmelden.setDescription("Sind Sie bereits registriert? Dann loggen Sie sich hier ein.");
        buttonAnmelden.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(Konstanten.LOGIN));


        Button buttonRegistrieren = new Button("Registrieren", FontAwesome.USER);
        buttonRegistrieren.setId("btnRegistration");
        buttonRegistrieren.setDescription("Registrieren Sie sich hier.");
        buttonRegistrieren.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(Konstanten.REGISTER));

        HorizontalLayout hz = new HorizontalLayout();

        //Baukasten zusammensetzen
        hz.addComponents(buttonAnmelden, buttonRegistrieren);
        this.addComponents(wilkommenText, hz);
        this.setComponentAlignment(wilkommenText, Alignment.TOP_CENTER);
        this.setComponentAlignment(hz, Alignment.MIDDLE_CENTER);
        this.setSizeFull();

    }
}
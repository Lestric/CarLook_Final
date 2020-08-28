package carlook.ui.views;

import carlook.control.controls.LoginControl;
import carlook.objects.dao.ProfilDAO;
import carlook.objects.dto.Kunde;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;


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

        Button btAutoView = new Button("Logout", FontAwesome.CAR);
        btAutoView.setDescription("Hier können Sie Autos suchen.");
        btAutoView.addClickListener(e ->{

        });

        // content
        FormLayout content = new FormLayout();
        content.addComponent(new Label("<b><p style=\"font-size:40px ; color:#fcba03; text-shadow: 1px 1px 2px black;\">CarLook</p></b>", ContentMode.HTML));
        content.addComponents(btAutoView, btLogout, btloeschen);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

    }

}
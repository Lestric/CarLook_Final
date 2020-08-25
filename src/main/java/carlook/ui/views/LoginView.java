package carlook.ui.views;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import carlook.control.controls.LoginControl;
import carlook.control.exceptions.DatabaseException;
import carlook.control.exceptions.NoSuchUserOrPassword;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Theme("mytheme")
@Title("Carlook - Login")
public class LoginView extends HorizontalLayout implements View {


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.setUp();

    }

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();

        final TextField emailField = new TextField();
        emailField.setId("fldEmail");
        emailField.setCaption("E-mail-Adresse:");


        final PasswordField passwordFieldLogin = new PasswordField();
        passwordFieldLogin.setId("fldPassword");
        passwordFieldLogin.setCaption("Passwort:");


        Button buttonLogin = new Button("Login", FontAwesome.USER);
        buttonLogin.setId("btnLogin");
        buttonLogin.setDescription("Loggen Sie sich mit Ihren Anmeldedaten ein.");
        buttonLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Label labelSpacer = new Label("&nbsp", ContentMode.HTML);

        VerticalLayout vl = new VerticalLayout();
        vl.setWidthFull();
        vl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        vl.addComponents(emailField, passwordFieldLogin, labelSpacer, buttonLogin);

        this.addComponents(vl);
        this.setSizeFull();
        this.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);



        ////////////////////////////////////////
        //hier Button Login Methode bei Click //
        ////////////////////////////////////////

        buttonLogin.addClickListener(e -> {

            String loginEmail = emailField.getValue();
            String password = passwordFieldLogin.getValue();

            try{
                LoginControl.checkAuthentication(loginEmail,password);

            } catch (NoSuchUserOrPassword noSuchUserOrPassword){
                Notification.show("Benutzerfehler", "Email-Adresse oder Passwort falsch!", Notification.Type.ERROR_MESSAGE);
                emailField.setValue("");
                passwordFieldLogin.setValue("");
            } catch (DatabaseException ex){
                Notification.show("DB-Fehler", ex.getReason(), Notification.Type.ERROR_MESSAGE);
                emailField.setValue("");
                passwordFieldLogin.setValue("");
            } catch (SQLException throwables) {
                Logger.getLogger(carlook.ui.views.LoginView.class.getName()).log(Level.SEVERE, null, throwables);
            }
        });
    }
}

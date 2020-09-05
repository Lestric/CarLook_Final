package carlook.ui.views;

import carlook.objects.dto.Kundedto;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import carlook.control.controls.RegistrationControl;
import carlook.control.exceptions.DatabaseException;
import carlook.services.util.*;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Theme("mytheme")
@Title("CarLook - Registrierung")


public class RegistrierungsView extends HorizontalLayout implements View {

    private String rolle = "kunde";
    private Kundedto kundedto =  new Kundedto();

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();


        Button regButton = new Button("Registrieren", VaadinIcons.USER);
        regButton.setId("btnRegistrieren");
        regButton.setDescription("Registrierung bestätigen.");
        regButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Label labelSpacer = new Label("&nbsp", ContentMode.HTML);


        ///////////////////////////////////////////////////
        // Erzeugen der Felder für die Registrierung:
        //////////////////////////////////////////////////

        final TextField vornameField = new TextField();
        vornameField.setCaption("Vorname: *");
        vornameField.setId("fldVorname");

        final TextField nachnameField = new TextField();
        nachnameField.setId("fldNachname");
        nachnameField.setCaption("Nachname: *");

        final TextField emailField = new TextField();
        emailField.setId("fldEmail");
        emailField.setCaption("Email-Adresse: *");

        final PasswordField passwortField = new PasswordField();
        passwortField.setId("fldPassword");
        passwortField.setCaption("Passwort: *");

        final PasswordField passwortField2 = new PasswordField();
        passwortField2.setId("fldPassword2");
        passwortField2.setCaption("Passwort bestätigen: *");


        VerticalLayout vl = new VerticalLayout();
        vl.setWidthFull();
        vl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        vl.addComponents(vornameField, nachnameField, emailField, passwortField, passwortField2, labelSpacer, regButton);

        this.addComponents(vl);
        this.setComponentAlignment(vl, Alignment.MIDDLE_CENTER);


        regButton.addClickListener(e ->{
            RegistrationResult r = null;

            if(rolle.equals(Roles.KUNDE)) {
                kundedto = KundeBuilder.getInstance().createNewUser().withEmail(emailField.getValue()).withPw(passwortField.getValue()).with2ndPw(passwortField2.getValue()).withVorname(vornameField.getValue()).withNachname(nachnameField.getValue()).getKundedto();
                kundedto.setRole(Roles.KUNDE);
            }

            try {
                r = RegistrationControl.getInstance().createRegistration(kundedto);
            } catch (SQLException | DatabaseException exception) {
                Logger.getLogger(carlook.ui.views.RegistrierungsView.class.getName()).log(Level.SEVERE, null, exception);
            }


            if (!r.getResult()) {
                //Gibt entsprechende Fehlermeldung aus
                Notification.show(r.error(), Notification.Type.ERROR_MESSAGE);
                //Passwortfelder werden bei Passwortfehlern geleert
                if (r.getFailureType() == RegistrationResult.FailureType.PASSWORD_TOO_SHORT || (r.getFailureType() == RegistrationResult.FailureType.PASSWORDS_DONT_MATCH)) {
                    passwortField.setValue("");
                    passwortField2.setValue("");
                }
                if( r.getFailureType() == RegistrationResult.FailureType.EMAIL_EXISTS){
                    emailField.setValue("");
                }
            } else {
                Notification.show("Registrierung Erfolgreich!", "Sie werden automatisch zum Login weitergeleitet.", Notification.Type.HUMANIZED_MESSAGE);
                UI.getCurrent().getNavigator().navigateTo(Konstanten.LOGIN);
            }
        });

    }

    public void enter(ViewChangeListener.ViewChangeEvent event){
        this.setUp();
    }
}
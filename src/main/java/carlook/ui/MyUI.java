package carlook.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import carlook.services.util.Konstanten;
import carlook.ui.views.*;


import javax.servlet.annotation.WebServlet;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */


@Theme("mytheme")
@Title("CarLook")
@PreserveOnRefresh


public class MyUI extends UI {

    private Navigator navi;


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        navi = new Navigator( this , this);


        navi.addView( Konstanten.START, StartseiteView.class );
        navi.addView( Konstanten.LOGIN, LoginView.class );
        navi.addView( Konstanten.REGISTER, RegistrierungsView.class );
        navi.addView( Konstanten.LANDINGPAGE, LandingPage.class );
        navi.addView(Konstanten.AUTOSUCHE, AutoSucheView.class);

        UI.getCurrent().getNavigator().navigateTo( Konstanten.START );


    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}



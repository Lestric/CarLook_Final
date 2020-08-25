package carlook.ui.views;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import carlook.ui.components.MenuDropDown;

@Title("Carlook")
@Theme("mytheme")



public class LandingPage extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {

            this.setUp();
    }

    public void setUp(){

        //Damit man nicht ein Stückchen scrollen kann
        this.setSizeFull();



        this.addComponents();
        this.setSizeFull();
        this.addComponent(new MenuDropDown());

    }

}
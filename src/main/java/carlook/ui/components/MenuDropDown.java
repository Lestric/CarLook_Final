package carlook.ui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import carlook.control.controls.LoginControl;
import carlook.services.util.Konstanten;


public class MenuDropDown extends HorizontalLayout {
    public MenuDropDown(){

        this.setWidth("100%");
        this.setHeight("30px");
        this.setDefaultComponentAlignment(Alignment.TOP_RIGHT);

        MenuBar bar = new MenuBar();
        bar.setId("drpDMenu");

        MenuBar.MenuItem item1 = bar.addItem("", VaadinIcons.MENU, null);
        

        item1.addItem("CarLook", VaadinIcons.SPECIALIST, (MenuBar.Command) menuItem -> UI.getCurrent().getNavigator().navigateTo(Konstanten.LANDINGPAGE));

        //Logout des Users
        item1.addItem("Logout", VaadinIcons.SIGN_OUT, (MenuBar.Command) selectedItem -> LoginControl.logoutUser());
        
        this.addComponent(bar);



    }
}

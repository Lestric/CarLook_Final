package carlook.ui.components;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class Content extends VerticalLayout {
    public Content(){
        this.addStyleName("content");
        this.setSpacing(true);
        this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        this.setWidth("100%");



    }
}

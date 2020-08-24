package carlook.ui.components;


import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

public class Sidebar extends VerticalLayout {
    public Sidebar(){
        this.setSpacing(true);
        this.addStyleName("start_sidebar");
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.setWidth("300px");


        final Label heading = new Label("");
        heading.setId("hdrTitle");
        heading.addStyleName("start_header");
        heading.setCaptionAsHtml(true);
        heading.setCaption("Carlook");
       
    

        Label labelSpacer = new Label("&nbsp", ContentMode.HTML);

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath + "/VAADIN/themes/mytheme/WEB-INF/auto.png"));

        Image image1 = new Image("", resource);
        image1.addStyleName("logo_format");

        this.addComponents(labelSpacer, image1, heading);
    }
}

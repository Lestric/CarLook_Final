package carlook.ui.components;


import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class Footer extends HorizontalLayout {
    public Footer(){
        this.setSizeFull();
        this.setHeight("30px");
        this.setWidth("100%");
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addStyleName("central_footer");
        String hbrs = "www.h-brs.de";
        String footerStyle = "central_footer_item";



        final Label delimeter = new Label("");
        delimeter.addStyleName(footerStyle);
        delimeter.setCaption("|");
        final Label delimeter2 = new Label("");
        delimeter2.addStyleName(footerStyle);
        delimeter2.setCaption("|");
        final Label delimeter3 = new Label("");
        delimeter3.addStyleName(footerStyle);
        delimeter3.setCaption("|");



        Link link1 = new Link("", getResource(hbrs));
        link1.setCaptionAsHtml(true);
        link1.setCaption("Kontakt");
        link1.addStyleName(footerStyle);
        Link link2 = new Link("", getResource(hbrs));
        link2.setCaptionAsHtml(true);
        link2.setCaption("Impressum");
        link2.addStyleName(footerStyle);
        Link link3 = new Link("", getResource(hbrs));
        link3.setCaptionAsHtml(true);
        link3.setCaption("Datenschutzerklärung");
        link3.addStyleName(footerStyle);


        NativeButton linkCompany = new NativeButton();
        linkCompany.setCaption("© Carlook");
        linkCompany.addStyleName(ValoTheme.BUTTON_LINK);
        linkCompany.addStyleName("company");
        linkCompany.setWidth("200px");


        this.addComponents(link1, delimeter, link2, delimeter2, link3, delimeter3, linkCompany);

    }
}

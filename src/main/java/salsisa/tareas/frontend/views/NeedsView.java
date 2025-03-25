package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.html.Image;

@PageTitle("SH - Visualizar Necesidades") // Nombre que sale arriba en el tab del navegador
@Route(value="NeedsView", layout = MainLayout.class) // Value indica la url y layout indica la clase que usa como base
@RouteAlias(value="", layout = MainLayout.class)
public class NeedsView extends VerticalLayout {
    public NeedsView() {
        setSpacing(false);
        setPadding(false);
        createHeader();
        createNeedsView();
    }
    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        //headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Seleccionar Necesidades");
        headingDiv.add(title);
        add(headingDiv);
    }
    private void createNeedsView() {
        //Se pueden ver 6 necesidades a la vez, 3 por fila
        VerticalLayout needsDiv = new VerticalLayout();
        needsDiv.setSpacing(false);
        add(needsDiv);
        needsDiv.setWidth("100%");
        //PRIMERA FILA
        HorizontalLayout firstAreaNeed = new HorizontalLayout();
        VerticalLayout firstNeed = new VerticalLayout();
        VerticalLayout secondNeed = new VerticalLayout();
        VerticalLayout thirdNeed = new VerticalLayout();
        firstAreaNeed.add(firstNeed, secondNeed, thirdNeed);
        //SEGUNDA FILA
        HorizontalLayout secondAreaNeed = new HorizontalLayout();
        VerticalLayout fourthNeed = new VerticalLayout();
        VerticalLayout fifthNeed = new VerticalLayout();
        VerticalLayout sixthNeed = new VerticalLayout();
        secondAreaNeed.add(fourthNeed, fifthNeed, sixthNeed);

        needsDiv.add(firstAreaNeed, secondAreaNeed);
    }
    private VerticalLayout createNeed(Image image, String label) {
        VerticalLayout need = new VerticalLayout();
        need.setWidth("100%");
        Span labelArea = new Span(label);
        need.setJustifyContentMode(JustifyContentMode.CENTER);
        need.add(image, labelArea);
        return need;
    }
}

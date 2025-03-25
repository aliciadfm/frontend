package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.Div;
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
        add(createNeed("Hola","Soy el sufijo"));
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
        //Se pueden ver 6 necesidades a la vez, 4 por fila
        VerticalLayout needsDiv = new VerticalLayout();
        needsDiv.setSpacing(false);
        add(needsDiv);
        needsDiv.setWidth("100%");
        //PRIMERA FILA
        HorizontalLayout firstAreaNeed = new HorizontalLayout();
        VerticalLayout firstNeed = new VerticalLayout();
        VerticalLayout secondNeed = new VerticalLayout();
        VerticalLayout thirdNeed = new VerticalLayout();
        VerticalLayout fourthNeed = new VerticalLayout();
        firstAreaNeed.add(firstNeed, secondNeed, thirdNeed, fourthNeed);
        //SEGUNDA FILA
        HorizontalLayout secondAreaNeed = new HorizontalLayout();
        VerticalLayout fifthNeed = new VerticalLayout();
        VerticalLayout sixthNeed = new VerticalLayout();
        VerticalLayout seventhNeed = new VerticalLayout();
        VerticalLayout eighthNeed = new VerticalLayout();
        secondAreaNeed.add(fifthNeed, sixthNeed, seventhNeed, eighthNeed);

        needsDiv.add(firstAreaNeed, secondAreaNeed);
    }
    private VerticalLayout createNeed(String label, String sufix) {
        VerticalLayout need = new VerticalLayout();
        need.setHeight("400px");
        need.setWidth("400px");
        HorizontalLayout foto = new HorizontalLayout();
        Div fotoprueba = new Div();
        fotoprueba.setWidth("100%");
        fotoprueba.setHeight("100%");
        foto.add(fotoprueba);
        foto.getStyle().set("border", "1px solid red");
        foto.setHeight("75%");
        foto.setWidth("100%");

        HorizontalLayout texto = new HorizontalLayout();
        texto.getStyle().set("border", "1px solid red");
        texto.setHeight("25%");
        texto.setWidth("100%");

        Span labelArea = new Span(label);
        Span sufixArea = new Span(sufix);
        texto.setJustifyContentMode(JustifyContentMode.CENTER);
        texto.add(labelArea, sufixArea);
        need.add(foto, texto);
        return need;
    }
}

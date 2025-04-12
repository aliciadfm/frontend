package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("SH - Visualizar tareas")
@Route(value="tareas", layout = MainLayout.class)
public class TareasView extends VerticalLayout {
    public TareasView() {
        setSpacing(false);
        setPadding(false);

        getStyle().set("padding", "0 5%");
        createHeader();
    }

    public void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.setWidth("100%");
        H1 title = new H1("Lista de tareas");
        headingDiv.add(title);
        add(headingDiv);
    }
}

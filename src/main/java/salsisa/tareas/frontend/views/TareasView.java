package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        createCentralContainer();
    }

    public void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Lista de tareas");
        headingDiv.add(title);
        add(headingDiv);
    }

    public void createCentralContainer() {
        HorizontalLayout centralContainer = new HorizontalLayout();
        centralContainer.getStyle().set("border", "1px solid red");
        centralContainer.setWidth("100%");
        add(centralContainer);
        centralContainer.add(volunteersListView());
    }

    public VerticalLayout volunteersListView() {
        VerticalLayout volunteersListView = new VerticalLayout();
        volunteersListView.getStyle().set("border", "1px solid red");
        volunteersListView.setWidth("100%");
        return volunteersListView;
    }
}

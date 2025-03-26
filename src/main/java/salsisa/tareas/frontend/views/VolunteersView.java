package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@PageTitle("Lista de voluntarios")
@Route(value="volunteers", layout = MainLayout.class)
public class VolunteersView extends VerticalLayout implements RouterLayout {
    public VolunteersView() {
        setSpacing(false);
        setPadding(false);
        getStyle().set("padding", "0 5%");
        createHeader();
    }

    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        //headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Lista de voluntarios");
        headingDiv.add(title);
        add(headingDiv);
    }



    private void createVolunteersList() {
    }
}

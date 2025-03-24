package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("SH - Crear tareas")
@Route(value="createTask", layout = MainLayout.class)
public class CreateTaskView extends VerticalLayout {
    public CreateTaskView() {
        setSpacing(false);
        setPadding(false);
        createHeader();
        createTextFields();
    }

    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Crear tarea");
        headingDiv.add(title);
        add(headingDiv);
    }

    private void createTextFields() {
        VerticalLayout fields = new VerticalLayout();
        add(fields);
        fields.getStyle().set("border", "1px solid red");
        Grid<VerticalLayout> fieldGrid = new Grid<>(VerticalLayout.class);
    }
}

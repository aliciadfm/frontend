package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.html.Image;

public class MainLayout extends AppLayout implements RouterLayout {
    public MainLayout() {
        DrawerToggle drawer = new DrawerToggle();
        addToNavbar(drawer);

        RouterLink needs = new RouterLink();
        RouterLink createTask = new RouterLink();
        RouterLink tasks = new RouterLink();

        needs.add(new Image("icons/needsVisualization.png", "Necesidades"));
        needs.setRoute(NeedsView.class);

        createTask.add(new Image("icons/taskCreation.png", "Crear tarea"));
        createTask.setRoute(CreateTaskView.class);

        tasks.add(new Image("icons/tareasVisualization.png", "Ver tareas"));
        tasks.setRoute(TareasView.class);

        VerticalLayout menu = new VerticalLayout(needs, createTask, tasks);
        menu.getStyle().set("background-color", "#B64040").setHeight("100%"); // Cambia el color
        menu.setAlignItems(Alignment.CENTER);
        menu.getStyle().set("padding-top", "10%");
        menu.setSpacing(true);

        addToDrawer(menu);
        setPrimarySection(Section.DRAWER);
    }
}


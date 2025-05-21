package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
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
        RouterLink logout= new RouterLink();

        Image needsImage = new Image("icons/needsVisualization.png", "Necesidades");
        needs.add(needsImage);
        needs.setRoute(NeedsView.class);
        Tooltip needsText = Tooltip.forComponent(needsImage);
        needsText.setText("Seleccionar necesidades");

        Image createTaskImage = new Image("icons/taskCreation.png", "Crear tarea");
        createTask.add(createTaskImage);
        createTask.setRoute(CreateTaskView.class);
        Tooltip createTaskText = Tooltip.forComponent(createTaskImage);
        createTaskText.setText("Crear tarea");

        Image taskViewImage = new Image("icons/tareasVisualization.png", "Ver tareas");
        tasks.add(taskViewImage);
        tasks.setRoute(TareasView.class);
        Tooltip tareasViewText = Tooltip.forComponent(taskViewImage);
        tareasViewText.setText("Lista de tareas");

        Image logoutImage = new Image("icons/logout.png", "Logout");
        logoutImage.setHeight("64px");
        logoutImage.setWidth("64px");
        logout.add(logoutImage);
        logout.setRoute(LoginView.class);
        Tooltip logoutText = Tooltip.forComponent(logoutImage);
        logoutText.setText("Cerrar sesion");

        VerticalLayout menu = new VerticalLayout(needs, createTask, tasks, logout);
        menu.getStyle().set("background-color", "#B64040").setHeight("100%"); // Cambia el color
        menu.setAlignItems(Alignment.CENTER);
        menu.getStyle().set("padding-top", "10%");
        menu.setSpacing(true);

        addToDrawer(menu);
        setPrimarySection(Section.DRAWER);
    }
}


package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.html.Image;



public class MainLayout extends AppLayout implements RouterLayout {
    public MainLayout() {
        DrawerToggle drawer = new DrawerToggle();
        addToNavbar(drawer);

        RouterLink needs = new RouterLink();
        RouterLink createTask = new RouterLink();

        needs.add(new Image("icons/needsVisualization.png", "Necesidades"));
        needs.setRoute(NeedsView.class);

        createTask.add(new Image("icons/taskCreation.png", "Crear tarea"));
        createTask.setRoute(CreateTaskView.class);

        VerticalLayout menu = new VerticalLayout(needs, createTask);
        menu.getStyle().set("background-color", "#B64040").setHeight("100%"); // Cambia el color
        menu.setAlignItems(Alignment.CENTER);
        menu.getStyle().set("padding-top", "10%");
        menu.setSpacing(true);

        addToDrawer(menu);
        setPrimarySection(Section.DRAWER);

        /*
        DrawerToggle drawer = new DrawerToggle();
        addToNavbar(drawer);
        RouterLink needs = new RouterLink("Necesidades", NeedsView.class);
        RouterLink createTask = new RouterLink("Crear tarea", CreateTaskView.class);
        RouterLink volunteers = new RouterLink("Voluntarios", VolunteersView.class);

        addToDrawer(new VerticalLayout(needs, createTask, volunteers));
        setPrimarySection(Section.DRAWER);
        */

        //IGNORAD ESTO, estoy averiguando cómo cambiar el color del menú
        //cambio de color de icono de guardar el menu
        //drawer.getStyle().set("background-color",  "#000000");


    }
}


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


public class MainLayout extends AppLayout implements RouterLayout {
    public MainLayout() {
        addToNavbar(new DrawerToggle());
        RouterLink needs = new RouterLink("Necesidades", NeedsView.class);
        RouterLink createTask = new RouterLink("Crear tarea", CreateTaskView.class);
        addToDrawer(new VerticalLayout(needs, createTask));
        setPrimarySection(Section.DRAWER);
    }
}


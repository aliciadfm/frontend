package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends Composite<VerticalLayout> {
    public MainLayout() {
        // Configuración del padre VerticalLayout
        VerticalLayout content = getContent();
        content.setWidthFull(); // Ancho lleno
        content.setHeightFull(); // Altura completa
        content.getStyle().set("display", "flex"); // Asegura un diseño basado en flexbox
        content.getStyle().set("flex-direction", "column"); // Columnas principales como eje
        content.getStyle().set("padding", "0");
        content.addClassName(LumoUtility.Gap.MEDIUM);

        // Row (HorizontalLayout)
        HorizontalLayout layoutRow = new HorizontalLayout();
        layoutRow.setWidthFull();
        layoutRow.setHeightFull(); // Hacer el row expandible en altura
        layoutRow.getStyle().set("flex-grow", "1"); // Garantiza crecimiento dentro del VerticalLayout

        // Columnas (VerticalLayout)
        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setWidth("8%");
        layoutColumn2.setHeightFull();
        layoutColumn2.getStyle().set("flex-grow", "1"); // Crecimiento en altura dentro del Row

        VerticalLayout layoutColumn3 = new VerticalLayout();
        layoutColumn3.setWidthFull();
        layoutColumn3.setHeightFull();
        layoutColumn3.getStyle().set("flex-grow", "1"); // Crecimiento en altura dentro del Row
        layoutColumn3.addClassNames(LumoUtility.Padding.MEDIUM);
        // Añade las columnas al HorizontalLayout
        layoutRow.add(layoutColumn2);
        layoutRow.add(layoutColumn3);

        // Añade las columnas al HorizontalLayout
        layoutRow.setSpacing(false);

        // Añade el row al padre VerticalLayout
        content.add(layoutRow);

        Button home = new Button("Home");
        Button pairing = new Button("Pairing");
        Button info = new Button("Information");
        Button logout = new Button("Logout");

        home.getStyle().set("background-color", "#FFFFFF");
        pairing.getStyle().set("background-color", "#FFFFFF");
        info.getStyle().set("background-color", "#FFFFFF");
        logout.getStyle().set("background-color", "#FFFFFF");

        home.getStyle().set("aspect-ratio", "1");
        home.getStyle().set("width", "100%");
        home.getStyle().set("padding", "0");
        //home.getStyle().set("aspect-ratio", "1").set("width", "100%");

        pairing.getStyle().set("aspect-ratio", "1");
        pairing.getStyle().set("width", "100%");
        pairing.getStyle().set("padding", "0");

        info.getStyle().set("aspect-ratio", "1");
        info.getStyle().set("width", "100%");
        info.getStyle().set("padding", "0");

        logout.getStyle().set("aspect-ratio", "1");
        logout.getStyle().set("width", "100%");
        logout.getStyle().set("padding", "0");

        layoutColumn2.add(home, pairing, info, logout);
        layoutColumn2.getStyle().set("background-color", "#B64040");

        home.addClickListener(e -> {
            UI.getCurrent().navigate("");
        });
        pairing.addClickListener(e -> {
            UI.getCurrent().navigate("pairing");
        });
        info.addClickListener(e -> {
            UI.getCurrent().navigate("information");
        });
    }
}


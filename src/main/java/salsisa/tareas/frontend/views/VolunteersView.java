package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.util.List;

@PageTitle("Lista de voluntarios")
@Route(value="volunteers", layout = MainLayout.class)
public class VolunteersView extends VerticalLayout implements RouterLayout {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;

    public VolunteersView(VoluntarioRestCliente voluntarioRestCliente) {
        this.voluntarioRestCliente = voluntarioRestCliente;
        setSpacing(false);
        setPadding(false);
        getStyle().set("padding", "0 5%");
        createHeader();
        createVolunteersList();
        createButtons();
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
        HorizontalLayout volunteersArea = new HorizontalLayout();
        volunteersArea.setWidth("100%");
        volunteersArea.setHeight("500px"); // Asigna una altura fija o usa setSizeFull()

        List<VoluntarioDTO> lista = voluntarioRestCliente.obtenerTodos();
        if (lista.isEmpty()) {
            throw new IllegalArgumentException("La lista de voluntarios está vacía.");
        }

        VirtualList<VoluntarioDTO> virtualList = new VirtualList<>();
        virtualList.setItems(lista);
        virtualList.setRenderer(voluntarioCardRenderer);
        virtualList.setHeight("100%"); // Mantén esto si el contenedor ya tiene altura

        volunteersArea.add(virtualList);
        add(volunteersArea); // Agregar el contenedor a la vista
    }

    private ComponentRenderer<Component, VoluntarioDTO> voluntarioCardRenderer = new ComponentRenderer<>(
            voluntario -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Avatar avatar = new Avatar(voluntario.getNombre());
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.add(new Span(voluntario.getNombre())); // Nombre del voluntario
                infoLayout.add(new Span("Email: " + voluntario.getEmail())); // Email

                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });

    private void createButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        add(buttons);
        buttons.setWidth("100%");
        buttons.setSpacing(false);
        buttons.setPadding(false);
        buttons.setMargin(false);
        Button cancelButton = new Button("Cancelar");
        Button acceptButton = new Button("Aceptar");
        HorizontalLayout cancelArea = new HorizontalLayout();
        cancelArea.add(cancelButton);
        cancelArea.setJustifyContentMode(JustifyContentMode.CENTER);
        cancelArea.setWidth("50%");
        HorizontalLayout acceptArea = new HorizontalLayout();
        acceptArea.add(acceptButton);
        acceptArea.setJustifyContentMode(JustifyContentMode.CENTER);
        acceptArea.setWidth("50%");
        buttons.add(cancelArea, acceptArea);
    }
}

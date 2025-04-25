package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.FiltroVoluntario1DTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.util.*;
import java.util.stream.Collectors;

@PageTitle("Lista de voluntarios")
@Route(value = "voluntarios", layout = MainLayout.class)
public class VolunteersView extends VerticalLayout implements RouterLayout {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;

    private final Map<VoluntarioDTO, Checkbox> checkboxMap = new HashMap<>();
    VirtualList<VoluntarioDTO> virtualList;

    public VolunteersView(VoluntarioRestCliente voluntarioRestCliente) {
        long startTime = System.currentTimeMillis();

        this.voluntarioRestCliente = voluntarioRestCliente;
        setSpacing(false);
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        getStyle().set("padding", "0 5%");
        createHeader();
        try {
            createVolunteersList();
        } catch (IllegalArgumentException e) {
            add(new Span("No hay voluntarios disponibles"));
        }
        createButtons();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime);
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
        volunteersArea.setHeight("500px");

        List<NecesidadDTO> necesidadesSeleccionadas = TaskFormData.getNecesidadesSeleccionadas();
        Set<VoluntarioDTO> totalVoluntarios = new HashSet<>();

        for (NecesidadDTO necesidad : necesidadesSeleccionadas) {
            FiltroVoluntario1DTO filtro = new FiltroVoluntario1DTO(
                    TaskFormData.getFechaInicio(),
                    TaskFormData.getFechaFin(),
                    TaskFormData.getTurnoManana(),
                    TaskFormData.getTurnoTarde(),
                    necesidad.getIdCategoria()
            );
            List<VoluntarioDTO> respuesta = voluntarioRestCliente.obtenerVoluntariosValidos(filtro);
            totalVoluntarios.addAll(respuesta);
        }

        if (totalVoluntarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de voluntarios está vacía.");
        }

        virtualList = new VirtualList<>();
        virtualList.setItems(totalVoluntarios); // Si aún no implementas paginación, al menos es una sola carga
        virtualList.setRenderer(voluntarioCardRenderer);
        virtualList.setHeight("100%");

        volunteersArea.add(virtualList);
        add(volunteersArea);
    }


    private final ComponentRenderer<Component, VoluntarioDTO> voluntarioCardRenderer = new ComponentRenderer<>(
            voluntario -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Avatar avatar = new Avatar(voluntario.getNombre());
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.setWidth("30%");
                infoLayout.add(new Span(voluntario.getNombre()));
                infoLayout.add(new Span("Email: " + voluntario.getEmail()));

                Checkbox checkBox = new Checkbox("");
                checkboxMap.put(voluntario, checkBox);

                cardLayout.add(avatar, infoLayout, checkBox);
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
        acceptButton.addClickListener(e -> {
            List<VoluntarioDTO> seleccionados = new ArrayList<>();

            for (Map.Entry<VoluntarioDTO, Checkbox> entry : checkboxMap.entrySet()) {
                if (entry.getValue().getValue()) {
                    seleccionados.add(entry.getKey());
                }
            }

            TaskFormData.setVoluntariosSeleccionados(seleccionados);
            UI.getCurrent().navigate(CreateTaskView.class);
        });

        acceptButton.getStyle().set("background-color", "#B64040");
        acceptButton.getStyle().set("color", "#ffffff");
        cancelButton.addClickListener(e -> {
            UI.getCurrent().navigate(CreateTaskView.class);
        });
    }
}

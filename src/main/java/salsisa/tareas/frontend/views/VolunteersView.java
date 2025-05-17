package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.FiltroVoluntario1DTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.dto.VoluntarioListadoDTO;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.util.*;

@PageTitle("Lista de voluntarios")
@Route(value = "voluntarios", layout = MainLayout.class)
public class VolunteersView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;

    private final Map<VoluntarioListadoDTO, Checkbox> checkboxMap = new HashMap<>();
    VirtualList<VoluntarioListadoDTO> virtualList;
    private String vistaOrigen;
    private Long tareaId;

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
        Set<VoluntarioListadoDTO> totalVoluntarios = new HashSet<>();
        NecesidadDTO necesidad = necesidadesSeleccionadas.getFirst();

        FiltroVoluntario1DTO filtro = new FiltroVoluntario1DTO(
                TaskFormData.getFechaInicio(),
                TaskFormData.getFechaFin(),
                TaskFormData.getTurnoManana(),
                TaskFormData.getTurnoTarde(),
                necesidad.getIdCategoria());
        List<VoluntarioListadoDTO> respuesta = voluntarioRestCliente.obtenerVoluntariosValidos(filtro);
        totalVoluntarios.addAll(respuesta);

        if (totalVoluntarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de voluntarios está vacía.");
        }

        virtualList = new VirtualList<>();
        virtualList.setItems(totalVoluntarios);
        virtualList.setRenderer(voluntarioCardRenderer);
        virtualList.setHeight("100%");

        volunteersArea.add(virtualList);
        add(volunteersArea);
    }


    private final ComponentRenderer<Component, VoluntarioListadoDTO> voluntarioCardRenderer = new ComponentRenderer<>(
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
            Set<VoluntarioListadoDTO> seleccionados = new HashSet<>();
            for (Map.Entry<VoluntarioListadoDTO, Checkbox> entry : checkboxMap.entrySet()) {
                if (entry.getValue().getValue()) {
                    seleccionados.add(entry.getKey());
                }
            }
            TaskFormData.getVoluntariosSeleccionados().addAll(seleccionados);
            if ("edit-task".equals(vistaOrigen)) {
                UI.getCurrent().navigate(EditTask.class, tareaId);
            } else {
                UI.getCurrent().navigate(CreateTaskView.class);
            }
        });

        acceptButton.getStyle().set("background-color", "#B64040");
        acceptButton.getStyle().set("color", "#ffffff");
        cancelButton.addClickListener(e -> {
            if ("edit-task".equals(vistaOrigen)) {
                UI.getCurrent().navigate(EditTask.class, tareaId);
            } else {
                UI.getCurrent().navigate(CreateTaskView.class);
            }
        });
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            String[] parts = parameter.split("/");

            if (parts.length >= 1) {
                vistaOrigen = parts[0];
            }

            if (parts.length >= 2) {
                try {
                    tareaId = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    Notification.show("ID de tarea inválido.");
                }
            }

            if ("edit-task".equals(vistaOrigen) && tareaId != null) {
                System.out.println("Vengo de edit-task con ID: " + tareaId);
            }
        }
    }

}

package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.TareaResumenDTO;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import java.util.function.Consumer;

public class TaskCard extends HorizontalLayout {
    TareaRestCliente cliente;
    public TaskCard(TareaResumenDTO tarea, TareaRestCliente cliente, Consumer<TaskCard> onDelete) {
        this.cliente = cliente;
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
        setMargin(true);
        getStyle().set("margin-left", "20px");
        getStyle().set("background-color", getColorForStatus(tarea.getEstado().toString()));
        getStyle().set("border-radius", "8px");
        getStyle().set("border", "1px solid #ccc");

        Span titulo = new Span(tarea.getTitulo());
        titulo.setWidth("12%");

        Span fechaInicio = new Span(tarea.getFechaInicio().toString());
        fechaInicio.setWidth("10%");

        Span fechaFin = new Span(tarea.getFechaFin().toString());
        fechaFin.setWidth("10%");

        Span localizacion = new Span(tarea.getPuntoEncuentro());
        localizacion.setWidth("12%");

        Span estado = new Span(tarea.getEstado().toString());
        estado.setWidth("10%");

        Integer numVol = tarea.getTotalAsistencias();
        Image icon = new Image("icons/numeroVoluntarios.png", "Voluntario");
        icon.setHeight("30px");
        icon.setWidth("30px");
        Span nVoluntarios = new Span(numVol.toString());
        HorizontalLayout voluntarios = new HorizontalLayout(icon,nVoluntarios);
        voluntarios.setAlignItems(Alignment.CENTER);
        voluntarios.setWidth("10%");

        VerticalLayout asistentes = new VerticalLayout();

        HorizontalLayout accept = new HorizontalLayout();
        HorizontalLayout reject = new HorizontalLayout();

        Integer acept = tarea.getTotalAceptadas();
        Span aceptada = new Span(acept.toString());
        Image si = new Image("icons/Tick.png", "Aceptada");
        si.setWidth("10px");
        si.setHeight("10px");
        accept.add(si, aceptada);
        accept.setAlignItems(Alignment.CENTER);

        Integer canc = tarea.getTotalCanceladas();
        Span cancelada = new Span(canc.toString());
        Image no = new Image("icons/Cross.png", "Cancelada");
        no.setWidth("15px");
        no.setHeight("15px");
        reject.add(no, cancelada);
        reject.setAlignItems(Alignment.CENTER);

        asistentes.add(accept,reject);
        asistentes.setWidth("10%");
        asistentes.setSpacing(true);


        if(tarea.getEstado() == Estado.TERMINADA) {
            add(titulo, fechaInicio, fechaFin, localizacion, estado, voluntarios, asistentes);
        }
        else {
            Image edit = new Image("icons/editarSimbolo.png", "Editar");
            edit.setWidth("30px");
            edit.setHeight("30px");
            Button editar = new Button(edit);
            editar.setWidth("5%");

            Image basura = new Image("icons/recycle.png", "Eliminar");
            basura.setWidth("30px");
            basura.setHeight("30px");
            Button eliminar = new Button(basura);
            editar.setWidth("5%");

            if(tarea.getEstado() == Estado.ENPROCESO) {
                estado.removeAll();
                estado.add("EN PROCESO");
            }
            add(titulo, fechaInicio, fechaFin, localizacion, estado, voluntarios, asistentes, editar, eliminar);

            editar.addClickListener(e -> {
                UI.getCurrent().navigate("editTask/" + tarea.getIdTarea());
            });

            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("Confirmar eliminación");

            Span message = new Span("¿Estás seguro de que quieres eliminar esta tarea?");

            Button confirmButton = new Button("Sí");
            confirmButton.addClickListener(event -> {
                confirmDialog.close();
                cliente.eliminar(tarea.getIdTarea());

                // Notifica al contenedor que elimine esta tarjeta
                onDelete.accept(this);
            });


            confirmButton.addClickListener(event -> {
                confirmDialog.close();
                cliente.eliminar(tarea.getIdTarea());


                onDelete.accept(this);
            });

            Button cancelButton = new Button("No", event -> confirmDialog.close());

            HorizontalLayout dialogButtons = new HorizontalLayout(confirmButton, cancelButton);
            confirmDialog.add(message, dialogButtons);

            eliminar.addClickListener(e -> {
                confirmDialog.open();
            });

        }

    }
    private String getColorForStatus(String status) {
        return switch (status) {
            case "PENDIENTE" -> "#ffb3b3"; // rojo claro
            case "TERMINADA" -> "#b3ffb3"; // verde claro
            case "ENPROCESO" -> "#ffffb3"; // amarillo claro
            case "ASIGNADA" -> "#ffd9b3"; // naranja claro
            default -> "white";
        };
    }
}

package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.TareaDTO;

public class TaskCard extends HorizontalLayout {
    public TaskCard(TareaDTO tarea) {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        Span titulo = new Span(tarea.getTitulo());
        Span fechaInicio = new Span(tarea.getFechaInicio().toString());
        Span fechaFin = new Span(tarea.getFechaFin().toString());
        Span localizacion = new Span(tarea.getPuntoEncuentro());
        Span estado = new Span(tarea.getEstado().toString());
        getStyle().set("background-color", getColorForStatus(tarea.getEstado().toString()));
        Integer numVol = tarea.getIdsVoluntarios().size();
        Image icon = new Image("icons/numeroVoluntarios.png", "Voluntario");
        Span nVoluntarios = new Span(numVol.toString());
        add(titulo, fechaInicio, fechaFin, localizacion, estado,icon,nVoluntarios);
    }
    private String getColorForStatus(String status) {
        return switch (status.toLowerCase()) {
            case "PENDIENTE" -> "#ffb3b3"; // rojo claro
            case "TERMINADA" -> "#b3ffb3"; // verde claro
            case "ENPROCESO" -> "#ffffb3"; // amarillo claro
            case "ASIGNADA" -> "#ffd9b3"; // naranja claro
            default -> "white";
        };
    }
}

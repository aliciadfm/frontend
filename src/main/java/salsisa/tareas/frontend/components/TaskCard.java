package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.TareaDTO;

public class TaskCard extends HorizontalLayout {
    public TaskCard(TareaDTO tarea) {
        Span titulo = new Span(tarea.getTitulo());
        Span fechaInicio = new Span(tarea.getFechaInicio().toString());
        Span fechaFin = new Span(tarea.getFechaFin().toString());
        Span localizacion = new Span(tarea.getPuntoEncuentro());
        Span estado = new Span(tarea.getEstado().toString());
        add(titulo, fechaInicio, fechaFin, localizacion, estado);
    }
}

package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.TareaResumenDTO;

public class TaskCard extends HorizontalLayout {
    public TaskCard(TareaResumenDTO tarea) {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        Span titulo = new Span(tarea.getTitulo());
        Span fechaInicio = new Span("");
        //Span fechaInicio = new Span(tarea.getFechaInicio().toString());
        Span fechaFin = new Span("");
        //Span fechaFin = new Span(tarea.getFechaFin().toString());
        Span localizacion = new Span("");
        //Span localizacion = new Span(tarea.getPuntoEncuentro());
        Span estado = new Span(tarea.getEstado().toString());
        Integer numVol = tarea.getTotalAsistencias();
        Image icon = new Image("icons/numeroVoluntarios.png", "Voluntario");
        icon.setHeight("20px");
        icon.setWidth("20px");
        Span nVoluntarios = new Span(numVol.toString());

        VerticalLayout asistentes = new VerticalLayout();

        HorizontalLayout accept = new HorizontalLayout();
        HorizontalLayout reject = new HorizontalLayout();

        Integer acept = tarea.getTotalAceptadas();
        Span aceptada = new Span(acept.toString());
        Image si = new Image("icons/Tick.png", "Aceptada");
        si.setWidth("10px");
        si.setHeight("10px");
        accept.add(si, aceptada);
        accept.getStyle().set("align-self", "center");

        Integer canc = tarea.getTotalCanceladas();
        Span cancelada = new Span(canc.toString());
        Image no = new Image("icons/Cross.png", "Cancelada");
        no.setWidth("10px");
        no.setHeight("10px");
        reject.add(no, cancelada);
        reject.getStyle().set("align-self", "center");

        asistentes.add(accept,reject);

        Image edit = new Image("icons/editarSimbolo.png", "Editar");
        edit.setWidth("20px");
        edit.setHeight("20px");
        Button editar = new Button(edit);
        add(titulo, fechaInicio, fechaFin, localizacion, estado, icon, nVoluntarios, asistentes, editar);
        getStyle().set("align-self", "center");
        getStyle().set("background-color", getColorForStatus(tarea.getEstado().toString()));
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

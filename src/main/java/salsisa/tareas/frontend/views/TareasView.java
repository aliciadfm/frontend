package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.components.TaskCard;
import salsisa.tareas.frontend.components.TaskFilters2;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.FiltroTareaDTO;
import salsisa.tareas.frontend.dto.TareaResumenDTO;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;

import java.util.Arrays;
import java.util.List;

@PageTitle("SH - Visualizar tareas")
@Route(value="tareas", layout = MainLayout.class)

public class TareasView extends VerticalLayout {
    @Autowired
    private TareaRestCliente tareaRestCliente;
    private VerticalLayout taskContainer;
    private TaskFilters2 taskFilters2;

    public TareasView(TareaRestCliente tareaRestCliente) {
        this.tareaRestCliente = tareaRestCliente;
        setSpacing(false);
        setPadding(false);

        getStyle().set("padding", "0 5%");
        createHeader();
        createCentralContainer();
    }

    public void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.setWidth("100%");
        headingDiv.setPadding(false);
        headingDiv.setSpacing(false);

        H1 title = new H1("Lista de tareas");
        title.getStyle()
                .set("margin-top", "40px")
                .set("margin-bottom", "20px");

        headingDiv.add(title);
        add(headingDiv);
    }

    public void createCentralContainer() {
        HorizontalLayout centralContainer = new HorizontalLayout();
        centralContainer.setWidth("100%");
        centralContainer.setSpacing(true);
        centralContainer.setPadding(false);
        centralContainer.getStyle().set("margin-top", "0px");

        taskFilters2 = new TaskFilters2();
        taskContainer = createTasksContainer();

        centralContainer.add(taskContainer, taskFilters2);
        add(centralContainer);

        taskFilters2.getBotonFiltrar().addClickListener(event -> {
            FiltroTareaDTO filtro = taskFilters2.getFiltroActual();
            actualizarListaTareasConFiltro(filtro);
        });
    }

    public VerticalLayout createTasksContainer() {
        VerticalLayout taskcreated = new VerticalLayout();
        taskcreated.setWidth("100%");
        taskcreated.setPadding(false);
        taskcreated.setSpacing(true);
        taskcreated.getStyle().set("margin-top", "0px");
        taskcreated.getStyle().set("gap", "10px");

        FiltroTareaDTO filtro = new FiltroTareaDTO();
        filtro.setEstados(Arrays.asList(
                Estado.ENPROCESO.name(),
                Estado.ASIGNADA.name(),
                Estado.TERMINADA.name(),
                Estado.PENDIENTE.name()
        ));

        List<TareaResumenDTO> listaTareas  = tareaRestCliente.obtenerTodos(filtro);
        for (TareaResumenDTO tarea : listaTareas) {
            HorizontalLayout task = new HorizontalLayout();
            task.setWidth("100%");
            task.setAlignItems(Alignment.CENTER);
            Div espacio = new Div();
            espacio.setWidth("60px"); // o el tamaño necesario para tu layout
            espacio.getStyle().set("padding-right", "3px"); // espacio con el contenido principal

            if (tarea.isAviso()) {
                Image warn = new Image("icons/Warning.png", "NoSuficientes");
                warn.setWidth("60px");
                warn.setHeight("60px");

                Tooltip nosuf = Tooltip.forComponent(warn);
                nosuf.setText("Demasiados voluntarios rechazaron la tarea");

                espacio.add(warn);
            }

            task.add(espacio);
            TaskCard taskCard = new TaskCard(tarea, tareaRestCliente, card -> {
                taskContainer.remove(task); // task es el HorizontalLayout que contiene la tarjeta
            });
            task.add(taskCard);
            taskcreated.add(task);
        }
        return taskcreated;
    }

    private void actualizarListaTareasConFiltro(FiltroTareaDTO filtro) {
        taskContainer.removeAll();

        List<TareaResumenDTO> listaTareas = tareaRestCliente.obtenerTodos(filtro);
        for (TareaResumenDTO tarea : listaTareas) {
            HorizontalLayout task = new HorizontalLayout();
            task.setWidth("100%");
            task.setAlignItems(Alignment.CENTER);

            Div espacio = new Div();
            espacio.setWidth("60px");
            espacio.getStyle().set("padding-right", "3px");

            if (tarea.isAviso()) {
                Image warn = new Image("icons/Warning.png", "NoSuficientes");
                warn.setWidth("60px");
                warn.setHeight("60px");

                Tooltip nosuf = Tooltip.forComponent(warn);
                nosuf.setText("Demasiados voluntarios rechazaron la tarea");

                espacio.add(warn);
            }

            task.add(espacio);
            TaskCard taskCard = new TaskCard(tarea, tareaRestCliente, card -> {
                taskContainer.remove(task); // task es el HorizontalLayout que contiene la tarjeta
            });
            task.add(taskCard);
            taskContainer.add(task);
        }
    }
}

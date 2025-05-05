package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.components.TaskCard;
import salsisa.tareas.frontend.components.TaskFilters;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.TareaResumenDTO;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("SH - Visualizar tareas")
@Route(value="tareas", layout = MainLayout.class)

public class TareasView extends VerticalLayout {
    @Autowired
    private TareaRestCliente tareaRestCliente;
    private VerticalLayout taskContainer;
    private TaskFilters taskFilters;

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
        H1 title = new H1("Lista de tareas");
        headingDiv.add(title);
        add(headingDiv);
    }

    public void createCentralContainer() {
        HorizontalLayout centralContainer = new HorizontalLayout();
        centralContainer.setWidth("100%");

        taskFilters = new TaskFilters();
        taskFilters.setEstadoChangeListener(this::updateTaskList);

        taskContainer = createTasksContainer();

        add(centralContainer);
        centralContainer.add(taskContainer, taskFilters);
    }

    public VerticalLayout createTasksContainer() {
        VerticalLayout taskcreated = new VerticalLayout();
        taskcreated.setWidth("100%");

        List<Estado> estado = new ArrayList();
        estado.add(Estado.ENPROCESO);
        estado.add(Estado.ASIGNADA);
        estado.add(Estado.TERMINADA);
        estado.add(Estado.PENDIENTE);
        List<TareaResumenDTO> listaTareas  = tareaRestCliente.filtrarPorEstados(estado);
        for (TareaResumenDTO tarea : listaTareas) {
            TaskCard taskCard = new TaskCard(tarea);
            taskcreated.add(taskCard);
        }
        return taskcreated;
    }

    private void updateTaskList(Estado selected) {
        taskContainer.removeAll();

        List<Estado> estadosFiltrados = new ArrayList<>();
        if (selected == null) {
            estadosFiltrados.addAll(Arrays.asList(Estado.values()));
        } else {
            estadosFiltrados.add(selected);
        }

        List<TareaResumenDTO> listaTareas = tareaRestCliente.filtrarPorEstados(estadosFiltrados);
        for (TareaResumenDTO tarea : listaTareas) {
            TaskCard taskCard = new TaskCard(tarea);
            taskContainer.add(taskCard);
        }
    }
}

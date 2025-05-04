package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.components.NeedFilters;
import salsisa.tareas.frontend.dto.*;
import salsisa.tareas.frontend.servicesAPI.CategoriaRestCliente;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import java.util.*;
import salsisa.tareas.frontend.components.NeedCard;

@PageTitle("SH - Visualizar Necesidades") // Nombre que sale arriba en el tab del navegador
@Route(value = "SelectMoreNeeds", layout = MainLayout.class)
public class SelectMoreNeeds extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    private NecesidadRestCliente necesidadRestCliente;
    @Autowired
    private CategoriaRestCliente categoriaRestCliente;
    private final List<NecesidadDTO> necesidadesSeleccionadas = new ArrayList<>();
    private final Map<NecesidadDTO, Checkbox> checkboxMap = new HashMap<>(); // este mapa es para relacionar cada necesidad con su correspondiente checkbox
    private Long categoriaId;
    private boolean comprobacion;

    public SelectMoreNeeds(NecesidadRestCliente necesidadRestCliente, CategoriaRestCliente categoriaRestCliente,
                           List<NecesidadDTO> necesidadesSeleccionadas) {
        this.necesidadRestCliente = necesidadRestCliente;
        this.categoriaRestCliente = categoriaRestCliente;
        if (!TaskFormData.getNecesidadesSeleccionadas().isEmpty()) {necesidadesSeleccionadas = TaskFormData.getNecesidadesSeleccionadas();}
        necesidadesSeleccionadas.forEach(n -> {
            if (this.necesidadesSeleccionadas.stream().noneMatch(existing -> existing.getIdNecesidad().equals(n.getIdNecesidad()))) {
                this.necesidadesSeleccionadas.add(n);
            }
        });
        setSpacing(false);
        setPadding(false);
        createHeader();

    }
    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        //headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Seleccionar Necesidades");
        headingDiv.add(title);
        add(headingDiv);
    }

    private List<NecesidadDTO> necesidadesCategorizadas() {
        FiltroNecesidadDTO vacio = new FiltroNecesidadDTO();
        List<NecesidadDTO> list = necesidadRestCliente.obtenerSinCubrir(vacio);
        List<NecesidadDTO> resultado = new ArrayList<>();
        for(NecesidadDTO necesidad : list) {
            if(necesidad.getIdCategoria().equals(this.categoriaId)) {
                resultado.add(necesidad);
            }
        }
        return resultado;
    }

    private void createNeedsView() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        FlexLayout gridLayout = new FlexLayout();
        gridLayout.setSizeFull();
        gridLayout.setWidth("100%");
        gridLayout.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")  // Para que se acomoden en filas
                .set("justify-content", "center") // Centra las tarjetas
                .set("gap", "10px"); // Espacio entre tarjetas
        FiltroNecesidadDTO vacio = new FiltroNecesidadDTO();
        List<NecesidadDTO> listaNecesidades = new ArrayList<>();
        if(comprobacion) {
            listaNecesidades = necesidadesCategorizadas();
        } else {
            listaNecesidades = necesidadRestCliente.obtenerSinCubrir(vacio);
        }
        System.out.println("Categoria ID: " + categoriaId);


        for (int i = 0; i < listaNecesidades.size(); i++) {
            NecesidadDTO necesidad = listaNecesidades.get(i);
            gridLayout.add(NeedCard.createCard(necesidad,true,necesidadesSeleccionadas,checkboxMap));
        }

        // Creamos el panel de filtros usando la nueva clase NeedFilters
        NeedFilters filtersContainer = new NeedFilters(categoriaRestCliente, filtros -> {

            // Cuando el usuario aplica filtros:
            gridLayout.removeAll();

            List<NecesidadDTO> necesidadesFiltradas = necesidadRestCliente.obtenerSinCubrir(filtros);
            if (!necesidadesFiltradas.isEmpty()) {
                for (NecesidadDTO necesidad : necesidadesFiltradas) {
                    gridLayout.add(NeedCard.createCard(necesidad,true,necesidadesSeleccionadas,checkboxMap));
                }
            } else {
                // Mostrar mensaje si no hay necesidades
                Div emptyMessage = new Div(new Span("No se encontraron necesidades con los filtros seleccionados."));
                emptyMessage.getStyle()
                        .set("padding", "20px")
                        .set("color", "#888")
                        .set("font-style", "italic");
                gridLayout.add(emptyMessage);
            }
        });

        mainLayout.add(gridLayout, filtersContainer);

        add(mainLayout);
        getStyle().set("padding", "0 2%");

        Button continuarBtn = new Button("Continuar con seleccionados");
        continuarBtn.getStyle().set("margin", "20px");
        continuarBtn.addClickListener(e -> {
            if (!necesidadesSeleccionadas.isEmpty() || checkboxMap.values().stream().anyMatch(Checkbox::getValue)) {
                Set<Long> idsYaAgregados = new HashSet<>();
                List<NecesidadDTO> seleccionados = new ArrayList<>();

                // Agrega los ya seleccionados si aún tienen el checkbox marcado
                for (Map.Entry<NecesidadDTO, Checkbox> entry : checkboxMap.entrySet()) {
                    if (entry.getValue().getValue()) {
                        Long id = entry.getKey().getIdNecesidad();
                        if (!idsYaAgregados.contains(id)) {
                            idsYaAgregados.add(id);
                            seleccionados.add(entry.getKey());
                        }
                    }
                }

                TaskFormData.setNecesidadesSeleccionadas(seleccionados);
                UI.getCurrent().navigate(CreateTaskView.class);
            } else {
                Notification.show("No has seleccionado ninguna necesidad.");
            }
        });
        add(continuarBtn);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long aLong) {
        if (aLong == null) {
            comprobacion = false;
        } else {
            comprobacion = true;
            this.categoriaId = aLong;
        }

        if (!TaskFormData.getNecesidadesSeleccionadas().isEmpty()) {
            TaskFormData.getNecesidadesSeleccionadas().forEach(n -> {
                if (this.necesidadesSeleccionadas.stream().noneMatch(existing -> existing.getIdNecesidad().equals(n.getIdNecesidad()))) {
                    this.necesidadesSeleccionadas.add(n);
                }
            });
        }

        createNeedsView();
    }
}


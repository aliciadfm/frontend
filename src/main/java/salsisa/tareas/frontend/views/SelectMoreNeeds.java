package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.*;
import salsisa.tareas.frontend.servicesAPI.CategoriaRestCliente;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;

import java.io.ByteArrayInputStream;
import java.util.*;

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
            FiltroNecesidadDTO filtro = new FiltroNecesidadDTO();
            listaNecesidades = necesidadRestCliente.obtenerSinCubrir(vacio);
        }
        System.out.println("Categoria ID: " + categoriaId);


        for (int i = 0; i < listaNecesidades.size(); i++) {
            gridLayout.add(createCard(listaNecesidades.get(i)));
        }
        VerticalLayout filtersContainer = new VerticalLayout();
        filtersContainer.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)");
        filtersContainer.setWidth("250px");
        filtersContainer.add(new H3("Filtros"));


        List<CategoriaDTO> listaCategoriasF = categoriaRestCliente.obtenerTodos();
        CheckboxGroup<String> categoryFilter = new CheckboxGroup<>();
        categoryFilter.setLabel("Categoría");
        List<String> cat = new ArrayList<>();
        for (int i = 0; i < listaCategoriasF.size(); i++){
            cat.add(listaCategoriasF.get(i).getNombre());
        }
        categoryFilter.setItems(cat);
        categoryFilter.setThemeName("vertical");
        filtersContainer.add(categoryFilter);


        //List<Urgencia> listaUrgenciasF = Arrays.stream(Urgencia.values()).toList();
        CheckboxGroup<Urgencia> urgencyFilter = new CheckboxGroup<>();
        urgencyFilter.setLabel("Urgencia");
        urgencyFilter.setItems(Urgencia.values());
        urgencyFilter.setThemeName("vertical");
        filtersContainer.add(urgencyFilter);


        //filtersContainer.getStyle().set("background-color", "grey");
        Button filtrar = new Button("Filtrar");

        filtrar.addClickListener(e -> {
            if (!categoryFilter.getSelectedItems().isEmpty() || !urgencyFilter.getSelectedItems().isEmpty()) {
                FiltroNecesidadDTO filtrosSeleccionados = new FiltroNecesidadDTO();

                if (!categoryFilter.getSelectedItems().isEmpty()) {
                    List<String> categoriasSeleccionadas = new ArrayList<>(categoryFilter.getSelectedItems());
                    List<Long> listaIdsCategoriasSeleccionadas = new ArrayList<>();
                    for (CategoriaDTO categoria : listaCategoriasF) {
                        if (categoriasSeleccionadas.contains(categoria.getNombre())) {
                            listaIdsCategoriasSeleccionadas.add(categoria.getIdCategoria());
                        }
                    }
                    filtrosSeleccionados.setCategorias(listaIdsCategoriasSeleccionadas);
                }
                if (!urgencyFilter.getSelectedItems().isEmpty()) {
                    List<Urgencia> urgenciasSeleccionadas = new ArrayList<>(urgencyFilter.getSelectedItems());
                    for (Urgencia urge : urgencyFilter.getSelectedItems()) {
                        urgenciasSeleccionadas.add(Urgencia.valueOf(urge.toString()));
                    }
                    filtrosSeleccionados.setUrgencias(urgenciasSeleccionadas);
                }
                // Esto sirve para guardar el estado de los checkbox antes de filtrar
                for (Map.Entry<NecesidadDTO, Checkbox> entry : checkboxMap.entrySet()) {
                    if (entry.getValue().getValue()) {
                        if (!necesidadesSeleccionadas.contains(entry.getKey())) {
                            necesidadesSeleccionadas.add(entry.getKey());
                        }
                    } else {
                        necesidadesSeleccionadas.removeIf(n -> n.getIdNecesidad().equals(entry.getKey().getIdNecesidad()));
                    }
                }
                mainLayout.removeAll();
                gridLayout.removeAll();
                

                List<NecesidadDTO> necesidadesFiltradas = necesidadRestCliente.obtenerSinCubrir(filtrosSeleccionados);
                if (!necesidadesFiltradas.isEmpty()) {
                    for (NecesidadDTO necesidad : necesidadesFiltradas) {
                        gridLayout.add(createCard(necesidad));
                    }
                } else {
                    // Para mantener el espacio aunque esté vacío
                    Div emptyMessage = new Div(new Span("No se encontraron necesidades con los filtros seleccionados."));
                    emptyMessage.getStyle()
                            .set("padding", "20px")
                            .set("color", "#888")
                            .set("font-style", "italic");

                    gridLayout.add(emptyMessage);
                }
                mainLayout.add(gridLayout, filtersContainer);
                add(mainLayout);

                getStyle().set("padding", "0 2%");

            } else {
                System.out.println("No se ha seleccionado ninguna categoría.");
            }
        });

        filtrar.getStyle().set("border", "1px solid #ccc")
                .set("border-radius", "10px");
        filtersContainer.add(filtrar);

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

    private Div createCard(NecesidadDTO necesidadDTO) {
        Div imageContainer = new Div();
        imageContainer.setWidth("100%");
        imageContainer.setHeight("150px");
        imageContainer.getStyle()
                .set("border", "2px dashed #ccc")
                .set("border-radius", "10px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background-color", "#f9f9f9");
        byte[] imagenBytes = necesidadDTO.getImagen();
        if (imagenBytes != null && imagenBytes.length > 0) {
            StreamResource resource = new StreamResource("imagen.png", () -> new ByteArrayInputStream(imagenBytes));
            Image imagen = new Image(resource, "Imagen de la necesidad");
            imagen.setWidth("100%");
            imagen.setHeight("100%");
            imagen.getStyle()
                    .set("object-fit", "cover") // Se ajusta bien al contenedor con mínima deformación
                    .set("border-radius", "10px"); // Borde redondeado igual que el contenedor
            imageContainer.add(imagen);
        } else {
            imageContainer.add(new Span("Imagen no disponible"));
        }

        H4 title = new H4(necesidadDTO.getNombre());
        title.setWidth("80%");
        Span categoryLabel = new Span("Disponible");
        categoryLabel.getStyle()
                .set("width", "80px") // Tamaño fijo
                .set("height", "30px") // Tamaño fijo
                .set("padding", "5px 10px")
                .set("border-radius", "10px")
                .set("font-weight", "bold")
                .set("bottom", "10px") // A 10 píxeles del borde inferior de la tarjeta
                .set("right", "10px"); // A 10 píxeles del borde derecho de la tarjeta
        categoryLabel.getStyle().set("background-color", "#67f913");

        Div bottomSection = new Div(title, categoryLabel);
        bottomSection.getStyle()
                .set("border-radius", "0 0 10px 10px")
                .set("padding", "10px")
                .set("display", "flex")
                .set("justify-content", "space-between");


        Span descriptionLabel = new Span(necesidadDTO.getDescripcion());
        descriptionLabel.getStyle()
                .set("margin-top", "10px")
                .set("display", "block");

        Checkbox checkbox = new Checkbox("Seleccionar necesidad");

        boolean yaSeleccionada = necesidadesSeleccionadas.stream()
                .anyMatch(n -> n.getIdNecesidad().equals(necesidadDTO.getIdNecesidad()));
        if (yaSeleccionada) {
            checkbox.setValue(true);
        }

        checkbox.addValueChangeListener(e -> {
            if (e.getValue()) {
                boolean yaSeleccionadas = necesidadesSeleccionadas.stream()
                        .anyMatch(n -> n.getIdNecesidad().equals(necesidadDTO.getIdNecesidad()));
                if (!yaSeleccionadas) {
                    necesidadesSeleccionadas.add(necesidadDTO);
                }

            }
            else{
                necesidadesSeleccionadas.removeIf(n -> n.getIdNecesidad().equals(necesidadDTO.getIdNecesidad()));
            }
        });

        Div descriptionContainer = new Div(descriptionLabel,checkbox);
        descriptionContainer.getStyle()
                .set("margin-top", "10px")
                .set("display", "flex")
                .set("flex-direction", "column");
        descriptionContainer.setVisible(false);

        Div card = new Div();
        card.add(imageContainer,bottomSection,descriptionContainer);
        card.getStyle()
                .set("flex", "1")     // Se ajusta dinámicamente
                .set("min-width", "250px")
                .set("max-width", "400px")
                .set("padding", "10px")
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)")
                .set("text-align", "left")
                .set("min-height", "150px")
                .set("background-color", "#fff")
                .set("transition", "all 0.3s ease");

        card.getElement().addEventListener("click", e -> {
            String clickedTag = e.getEventData().getString("event.target.tagName");

            if ("VAADIN-CHECKBOX".equals(clickedTag) || "LABEL".equals(clickedTag) || "INPUT".equals(clickedTag)) {
                return; // No expandas si se hizo clic en el checkbox
            }

            boolean isExpanded = descriptionContainer.isVisible();
            descriptionContainer.setVisible(!isExpanded);

            if (!isExpanded) {
                card.getStyle()
                        .set("height", "auto")
                        .set("box-shadow", "4px 4px 8px rgba(0,0,0,0.2)");
            } else {
                card.getStyle()
                        .set("height", "auto")
                        .set("box-shadow", "2px 2px 5px rgba(0,0,0,0.1)");
            }
        }).addEventData("event.target.tagName");

        checkboxMap.put(necesidadDTO, checkbox); // así tenemos una manera fácil de acceder a la necesidad a partir de una checkbox
        return card;
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


package salsisa.tareas.frontend.components;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import salsisa.tareas.frontend.dto.Estado;
import java.util.*;
import java.util.function.Consumer;

public class TaskFilters extends VerticalLayout {
    private CheckboxGroup<String> estadoSeleccionado = new CheckboxGroup<>();

    private Consumer<Estado> estadoChangeListener;


    private static final String MOSTRAR_TODO = "Mostrar todo";

    private final Map<Estado, String> etiquetasEstado = new LinkedHashMap<>();
    private final Map<String, Estado> etiquetaAEstado = new HashMap<>();

    public TaskFilters() {
        setWidth("250px");
        getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)");

        add(new H3("Estado"));
        createEstadoFilter();
        add(estadoSeleccionado);
    }

    private void createEstadoFilter() {
        // Mapeamos los nombres legibles
        etiquetasEstado.put(Estado.TERMINADA, "Terminada");
        etiquetasEstado.put(Estado.ENPROCESO, "En proceso");
        etiquetasEstado.put(Estado.ASIGNADA, "Asignada");
        etiquetasEstado.put(Estado.PENDIENTE, "Pendiente");

        // Invertimos el mapa para buscar fácilmente después
        etiquetasEstado.forEach((estado, etiqueta) -> etiquetaAEstado.put(etiqueta, estado));

        List<String> opciones = new ArrayList<>();
        opciones.add(MOSTRAR_TODO);
        opciones.addAll(etiquetasEstado.values());

        estadoSeleccionado.setItems(opciones);
        estadoSeleccionado.setThemeName("vertical");
        estadoSeleccionado.setValue(Set.of(MOSTRAR_TODO));

        estadoSeleccionado.addValueChangeListener(e -> {
            Set<String> selected = e.getValue();

            if (selected.size() > 1) {
                String lastSelected = e.getValue().stream()
                        .filter(item -> !e.getOldValue().contains(item))
                        .findFirst()
                        .orElse(MOSTRAR_TODO);
                estadoSeleccionado.setValue(Set.of(lastSelected));
            } else if (selected.isEmpty()) {
                estadoSeleccionado.setValue(Set.of(MOSTRAR_TODO));
            }

            if (estadoChangeListener != null) {
                estadoChangeListener.accept(getSelectedEstado());
            }
        });
    }

    /* Devuelve el estado seleccionado o null si es "Mostrar tod"*/
    public Estado getSelectedEstado() {
        String seleccion = estadoSeleccionado.getValue().stream().findFirst().orElse(null);
        if (MOSTRAR_TODO.equals(seleccion)) {
            return null;
        }
        return etiquetaAEstado.get(seleccion);
    }

    public void setEstadoChangeListener(Consumer<Estado> listener) {
        this.estadoChangeListener = listener;
    }

}

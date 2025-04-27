package salsisa.tareas.frontend.components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import salsisa.tareas.frontend.dto.CategoriaDTO;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.Urgencia;
import salsisa.tareas.frontend.servicesAPI.CategoriaRestCliente;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NeedFilters extends VerticalLayout {

    private CheckboxGroup<String> categoryFilter;
    private CheckboxGroup<Urgencia> urgencyFilter;
    private Button filtrarButton;
    private List<CategoriaDTO> listaCategorias;

    public NeedFilters(CategoriaRestCliente categoriaRestCliente, Consumer<FiltroNecesidadDTO> onFilterApplied) {
        this.listaCategorias = categoriaRestCliente.obtenerTodos();

        setWidth("250px");
        getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)");

        add(new H3("Filtros"));

        createCategoryFilter();
        createUrgencyFilter();
        createFiltrarButton(onFilterApplied);

        add(categoryFilter, urgencyFilter, filtrarButton);
    }

    private void createCategoryFilter() {
        categoryFilter = new CheckboxGroup<>();
        categoryFilter.setLabel("Categoría");
        List<String> nombresCategorias = new ArrayList<>();
        for (CategoriaDTO categoria : listaCategorias) {
            nombresCategorias.add(categoria.getNombre());
        }
        categoryFilter.setItems(nombresCategorias);
        categoryFilter.setThemeName("vertical");
    }

    private void createUrgencyFilter() {
        urgencyFilter = new CheckboxGroup<>();
        urgencyFilter.setLabel("Urgencia");
        urgencyFilter.setItems(Urgencia.values());
        urgencyFilter.setThemeName("vertical");
    }

    private void createFiltrarButton(Consumer<FiltroNecesidadDTO> onFilterApplied) {
        filtrarButton = new Button("Filtrar", click -> {
            FiltroNecesidadDTO filtrosSeleccionados = new FiltroNecesidadDTO();

            if (!categoryFilter.getSelectedItems().isEmpty()) {
                List<String> categoriasSeleccionadas = new ArrayList<>(categoryFilter.getSelectedItems());
                List<Long> listaIdsCategoriasSeleccionadas = new ArrayList<>();
                for (CategoriaDTO categoria : listaCategorias) {
                    if (categoriasSeleccionadas.contains(categoria.getNombre())) {
                        listaIdsCategoriasSeleccionadas.add(categoria.getIdCategoria());
                    }
                }
                filtrosSeleccionados.setCategorias(listaIdsCategoriasSeleccionadas);
            }

            if (!urgencyFilter.getSelectedItems().isEmpty()) {
                filtrosSeleccionados.setUrgencias(new ArrayList<>(urgencyFilter.getSelectedItems()));
            }

            onFilterApplied.accept(filtrosSeleccionados);
        });

        filtrarButton.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px");
    }
}

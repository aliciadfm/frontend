package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.components.NeedCard;
import salsisa.tareas.frontend.components.NeedFilters;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.servicesAPI.CategoriaRestCliente;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import java.util.List;

@PageTitle("SH - Visualizar Necesidades") // Nombre que sale arriba en el tab del navegador
@Route(value="NeedsView", layout = MainLayout.class) // Value indica la url y layout indica la clase que usa como base
@RouteAlias(value="", layout = MainLayout.class)


public class NeedsView extends VerticalLayout {

    @Autowired
    private NecesidadRestCliente necesidadRestCliente;
    @Autowired
    private CategoriaRestCliente categoriaRestCliente;

    public NeedsView(NecesidadRestCliente necesidadRestCliente, CategoriaRestCliente categoriaRestCliente) {
        this.necesidadRestCliente = necesidadRestCliente;
        this.categoriaRestCliente = categoriaRestCliente;
        setSpacing(false);
        setPadding(false);
        createHeader();
        createNeedsView();
    }
    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.setWidth("100%");
        H1 title = new H1("Seleccionar Necesidades");
        headingDiv.add(title);
        add(headingDiv);
    }
    private void createNeedsView() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        FlexLayout gridLayout = new FlexLayout();
        gridLayout.setSizeFull();
        gridLayout.setWidth("100%");
        gridLayout.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap") // Para que se acomoden en filas
                .set("justify-content", "center") // Centrar tarjetas
                .set("gap", "10px"); // Espacio entre tarjetas

        // Mostrar inicialmente todas las necesidades
        FiltroNecesidadDTO vacio = new FiltroNecesidadDTO();
        List<NecesidadDTO> listaNecesidades = necesidadRestCliente.obtenerSinCubrir(vacio);
        for (NecesidadDTO necesidad : listaNecesidades) {
            gridLayout.add(NeedCard.createCard(necesidad));
        }

        // Creamos el panel de filtros usando la nueva clase NeedFilters
        NeedFilters filtersContainer = new NeedFilters(categoriaRestCliente, filtros -> {

            // Cuando el usuario aplica filtros:
            gridLayout.removeAll();

            List<NecesidadDTO> necesidadesFiltradas = necesidadRestCliente.obtenerSinCubrir(filtros);
            if (!necesidadesFiltradas.isEmpty()) {
                for (NecesidadDTO necesidad : necesidadesFiltradas) {
                    gridLayout.add(NeedCard.createCard(necesidad));
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

        // Añadimos ambos layouts a la vista principal
        mainLayout.add(gridLayout, filtersContainer);
        add(mainLayout);

        getStyle().set("padding", "0 2%");
    }

}

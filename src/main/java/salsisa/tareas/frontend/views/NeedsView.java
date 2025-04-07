package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.*;
                        import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;

import java.util.List;

@PageTitle("SH - Visualizar Necesidades") // Nombre que sale arriba en el tab del navegador
@Route(value="NeedsView", layout = MainLayout.class) // Value indica la url y layout indica la clase que usa como base
@RouteAlias(value="", layout = MainLayout.class)
public class NeedsView extends VerticalLayout {

    @Autowired
    private NecesidadRestCliente necesidadRestCliente;

    public NeedsView(NecesidadRestCliente necesidadRestCliente) {
        this.necesidadRestCliente = necesidadRestCliente;
        setSpacing(false);
        setPadding(false);
        createHeader();
        createNeedsView();
    }
    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        //headingDiv.getStyle().set("border", "1px solid red");
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
                .set("flex-wrap", "wrap")  // Para que se acomoden en filas
                .set("justify-content", "center") // Centra las tarjetas
                .set("gap", "10px"); // Espacio entre tarjetas
        List<NecesidadDTO> listaNecesidades = necesidadRestCliente.obtenerTodos();
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
        CheckboxGroup<String> categoryFilter = new CheckboxGroup<>();
        categoryFilter.setLabel("Categoría");
        categoryFilter.setItems("Alimentación", "Salud", "Limpieza");
        categoryFilter.setThemeName("vertical");
        filtersContainer.add(categoryFilter);
        CheckboxGroup<String> urgencyFilter = new CheckboxGroup<>();
        urgencyFilter.setLabel("Urgencia");
        urgencyFilter.setItems("Alta", "Media", "Baja");
        urgencyFilter.setThemeName("vertical");
        filtersContainer.add(urgencyFilter);
        //filtersContainer.getStyle().set("background-color", "grey");
        mainLayout.add(gridLayout, filtersContainer);
        add(mainLayout);
        getStyle().set("padding", "0 2%");

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
        imageContainer.add(new Span("Imagen no disponible"));            // aqui se cambiara para la imagen, ahora esta puesto q no hay imagen
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
        descriptionLabel.setVisible(false);

        Div card = new Div();
        card.add(imageContainer,bottomSection,descriptionLabel);
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

        card.addClickListener(event -> {
            boolean isExpanded = descriptionLabel.isVisible();
            descriptionLabel.setVisible(!isExpanded);
            if (!isExpanded) {
                card.getStyle()
                        .set("height", "auto")
                        .set("box-shadow", "4px 4px 8px rgba(0,0,0,0.2)");
            } else {
                card.getStyle()
                        .set("height", "auto")
                        .set("box-shadow", "2px 2px 5px rgba(0,0,0,0.1)");
            }
        });


        return card;
    }
}

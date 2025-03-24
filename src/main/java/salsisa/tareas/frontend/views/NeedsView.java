package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Solidarity Hub") // Nombre que sale arriba en el tab del navegador
@Route(value="", layout = MainLayout.class) // Value indica la url y layout indica la clase que usa como base
public class NeedsView extends HorizontalLayout {
    public NeedsView() {
        // He dividido esta vista en dos zonas/VerticalLayouts. Filters tendrá los filtros, y needsCardsSection es
        // estarán todas las necesidades para seleccionarlas. Les he añadido un borde rojo temporal para poder ver
        // lo que ocupan las secciones. Luego se borrará.
        setSpacing(false); // Por defecto, un Layout añade margen entre sus componentes. Lo quito con esto.
        VerticalLayout filters = new VerticalLayout();
        VerticalLayout needsCardsSection = new VerticalLayout();
        filters.getStyle().set("border", "1px solid red");
        filters.setWidth("25%");

        needsCardsSection.getStyle().set("border", "1px solid red");

        add(filters, needsCardsSection);
    }
}

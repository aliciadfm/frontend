package salsisa.tareas.frontend.views;

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
        FlexLayout gridLayout = new FlexLayout();
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
        add(gridLayout);
        getStyle().set("padding", "0 2%");
/*
        //Se pueden ver 6 necesidades a la vez, 4 por fila
        VerticalLayout needsDiv = new VerticalLayout();
        needsDiv.setSpacing(false);
        add(needsDiv);
        needsDiv.setWidth("100%");
        //PRIMERA FILA
        HorizontalLayout firstAreaNeed = new HorizontalLayout();
        VerticalLayout firstNeed = new VerticalLayout();
        VerticalLayout secondNeed = new VerticalLayout();
        VerticalLayout thirdNeed = new VerticalLayout();
        VerticalLayout fourthNeed = new VerticalLayout();
        firstAreaNeed.add(firstNeed, secondNeed, thirdNeed, fourthNeed);
        //SEGUNDA FILA
        HorizontalLayout secondAreaNeed = new HorizontalLayout();
        VerticalLayout fifthNeed = new VerticalLayout();
        VerticalLayout sixthNeed = new VerticalLayout();
        VerticalLayout seventhNeed = new VerticalLayout();
        VerticalLayout eighthNeed = new VerticalLayout();
        secondAreaNeed.add(fifthNeed, sixthNeed, seventhNeed, eighthNeed);

        needsDiv.add(firstAreaNeed, secondAreaNeed);

 */
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
        H6 subtitulo = new H6("Disponible");
        subtitulo.setWidth("20%");
        subtitulo.getStyle().set("text-align", "right");
        Span margenDerecho = new Span();
        margenDerecho.setWidth("10px");
        HorizontalLayout orden = new HorizontalLayout(title, subtitulo, margenDerecho);
        orden.getStyle().set("padding", "10px");
        Div card = new Div(imageContainer, orden);
        card.getStyle()
                .set("flex", "1")                                               // Se ajusta dinámicamente
                .set("min-width", "250px")
                .set("max-width", "400px")
                .set("padding", "10px")
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)")
                .set("text-align", "left");
        return card;
    }
}

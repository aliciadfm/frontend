package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;
import salsisa.tareas.frontend.views.CreateTaskView;

import java.util.Arrays;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class NeedCard extends Div {
    public static Div createCard(NecesidadDTO necesidadDTO, Boolean check, List<NecesidadDTO> necesidadesSeleccionadas, Map<NecesidadDTO, Checkbox> checkboxMap) {
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
            byte[] imageData = Arrays.copyOf(imagenBytes, imagenBytes.length); // Copia defensiva
            StreamResource resource = new StreamResource("imagen.png", () -> {
                return new ByteArrayInputStream(imageData); // Nunca será null
            });
            Image imagen = new Image(resource, "Imagen de la necesidad");
            imagen.setWidth("100%");
            imagen.setHeight("100%");
            imagen.getStyle()
                    .set("object-fit", "cover")
                    .set("border-radius", "10px");
            imageContainer.add(imagen);
        } else {
            imageContainer.add(new Span("Imagen no disponible"));
        }

        Div locationContainer = new Div();
        locationContainer.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("margin-top", "8px")
                .set("gap", "5px");

        Image locationIcon = new Image("icons/location.png", "Localización");
        locationIcon.setWidth("30px");
        locationIcon.setHeight("30px");

        Span locationText = new Span(
                necesidadDTO.getZona() != null
                        ? necesidadDTO.getZona()
                        : "Ubicación no disponible"
        );
        locationText.getStyle()
                .set("background-color", "#f1f1f1")
                .set("padding", "3px 10px")
                .set("border-radius", "8px")
                .set("font-weight", "bold");
        locationText.setWidth("100%");

        locationContainer.add(locationIcon, locationText);

        H4 title = new H4(necesidadDTO.getNombre());
        Span categoryLabel = new Span("Disponible");
        categoryLabel.getStyle()
                .set("background-color", "#B1EEA1")
                .set("padding", "5px 10px")
                .set("border-radius", "10px")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .setWidth("90px")
                .setHeight("25px");

        Div bottomSection = new Div(title, categoryLabel);
        bottomSection.getStyle()
                .set("display", "flex")
                .set("justify-content", "space-between")
                .set("padding", "10px");

        Div descriptionContainer = new Div();
        Span descriptionLabel = new Span(necesidadDTO.getDescripcion());

        if (check) {
            Checkbox checkbox = new Checkbox("Seleccionar necesidad");

            boolean yaSeleccionada = necesidadesSeleccionadas.stream()
                    .anyMatch(n -> n.getIdNecesidad().equals(necesidadDTO.getIdNecesidad()));
            if (yaSeleccionada) {
                checkbox.setValue(true);
                categoryLabel.setText("Seleccionado");
                categoryLabel.getStyle().set("background-color", "#efc48f");
            }

            checkbox.addValueChangeListener(e -> {
                if (e.getValue()) {
                    Long categoriaActual = necesidadDTO.getIdCategoria();
                    boolean categoriaDiferente = necesidadesSeleccionadas.stream()
                            .anyMatch(n -> !n.getIdCategoria().equals(categoriaActual));
                    if (categoriaDiferente) {
                        Notification.show("Solo puedes seleccionar necesidades de una misma categoría.");
                        checkbox.setValue(false);
                        return;
                    }
                    necesidadesSeleccionadas.add(necesidadDTO);
                    categoryLabel.setText("Seleccionado");
                    categoryLabel.getStyle().set("background-color", "#efc48f");
                } else {
                    necesidadesSeleccionadas.removeIf(n -> n.getIdNecesidad().equals(necesidadDTO.getIdNecesidad()));
                    categoryLabel.setText("Disponible");
                    categoryLabel.getStyle().set("background-color", "#b1eea1");
                }
            });
            checkboxMap.put(necesidadDTO, checkbox);
            descriptionContainer .add(descriptionLabel,checkbox);
        }

        else {
            Button select = new Button("Seleccionar", event -> {
                List<NecesidadDTO> necesidadSeleccionada = new ArrayList<>();
                necesidadSeleccionada.add(necesidadDTO);
                TaskFormData.setNecesidadesSeleccionadas(necesidadSeleccionada);
                UI.getCurrent().navigate(CreateTaskView.class);
            });

            descriptionContainer.add(descriptionLabel, select);
        }

        descriptionContainer.getStyle()
                .set("margin-top", "10px")
                .set("display", "flex")
                .set("flex-direction", "column");
        descriptionContainer.setVisible(false);

        Div card = new Div(imageContainer,locationContainer, bottomSection, descriptionContainer);
        card.getStyle()
                .set("flex", "1")
                .set("min-width", "250px")
                .set("max-width", "400px")
                .set("padding", "10px")
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)")
                .set("background-color", "#fff")
                .set("transition", "all 0.3s ease");

        card.addClickListener(event -> {
            boolean isExpanded = descriptionContainer.isVisible();
            descriptionContainer.setVisible(!isExpanded);
            card.getStyle().set("box-shadow", isExpanded ?
                    "2px 2px 5px rgba(0,0,0,0.1)" : "4px 4px 8px rgba(0,0,0,0.2)");
        });

        return card;
    }
}

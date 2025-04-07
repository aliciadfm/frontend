package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.checkbox.Checkbox;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.time.Duration;
import java.util.List;

@PageTitle("SH - Crear tareas")
@Route(value="createTask", layout = MainLayout.class)
public class CreateTaskView extends VerticalLayout {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;
    @Autowired
    private NecesidadRestCliente necesidadRestCliente;

    private TextField tituloField;
    private TextField descripcionField;
    private DateTimePicker inicioPicker;
    private DateTimePicker finPicker;
    private Checkbox pendienteCheckbox;

    public CreateTaskView(VoluntarioRestCliente voluntarioRestCliente, NecesidadRestCliente necesidadRestCliente) {
        this.voluntarioRestCliente = voluntarioRestCliente;
        this.necesidadRestCliente = necesidadRestCliente;
        setSpacing(false);
        setPadding(false);
        createHeader();
        createTextFields();
        createCheckboxArea();
        createButton();
    }

    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        //headingDiv.getStyle().set("border", "1px solid red");
        headingDiv.setWidth("100%");
        H1 title = new H1("Crear tarea");
        headingDiv.add(title);
        add(headingDiv);
    }

    private HorizontalLayout createFieldArea(String label, Component field) {
        HorizontalLayout area = new HorizontalLayout();
        area.setWidth("100%");
        //area.getStyle().set("border", "1px solid red");
        VerticalLayout labelArea = new VerticalLayout();
        labelArea.setSpacing(false);
        labelArea.setPadding(false);
        labelArea.setWidth("15%");
        //labelArea.getStyle().set("border", "1px solid red");
        Span span = new Span(label);
        labelArea.setAlignSelf(Alignment.END, span);
        labelArea.setJustifyContentMode(JustifyContentMode.CENTER);
        labelArea.add(span);
        area.add(labelArea);

        VerticalLayout fieldArea = new VerticalLayout();
        fieldArea.setSpacing(false);
        fieldArea.setMargin(false);
        fieldArea.setWidth("100%");
        fieldArea.add(field);
        if (field != null) {
            ((HasSize) field).setWidth("100%");
        }

        area.add(fieldArea);
        return area;
    }

    private void createTextFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(false);
        add(fields);
        fields.setWidth("100%");

        tituloField = new TextField("");
        descripcionField = new TextField("");
        inicioPicker = new DateTimePicker("");
        finPicker = new DateTimePicker("");

        //COLUMNA DE LA IZQUIERDA
        VerticalLayout column1 = new VerticalLayout();
        //column1.getStyle().set("border", "1px solid red");
        HorizontalLayout tituloFieldArea = createFieldArea("Título", tituloField);
        HorizontalLayout descripcionFieldArea = createFieldArea("Descripción", descripcionField);
        DateTimePicker inicio = new DateTimePicker("");
        inicio.setStep(Duration.ofMinutes(30));
        HorizontalLayout inicioFieldArea = createFieldArea("Inicio", inicioPicker);

        DateTimePicker fin = new DateTimePicker("");
        fin.setStep(Duration.ofMinutes(30));
        HorizontalLayout finFieldArea = createFieldArea("Fin", finPicker);

        //COLUMNA DE LA DERECHA
        VerticalLayout column2 = new VerticalLayout();

        VerticalLayout voluntariosArea = new VerticalLayout();
        voluntariosArea.setSpacing(false);
        voluntariosArea.setPadding(false);

        VerticalLayout necesidadesArea = new VerticalLayout();
        necesidadesArea.setSpacing(false);
        necesidadesArea.setPadding(false);

        Button voluntariosButton = new Button("Añdir voluntarios");
        Button necesidadesButton = new Button("Añadir necesidades");
        voluntariosButton.addClickListener(e -> {
            UI.getCurrent().navigate(VolunteersView.class);
        });
        HorizontalLayout voluntariosFieldArea = createFieldArea("Voluntarios", voluntariosButton);
        HorizontalLayout necesidadesFieldArea = createFieldArea("Necesidades", necesidadesButton);

        List<VoluntarioDTO> listaVoluntarios = voluntarioRestCliente.obtenerTodos();
        List<NecesidadDTO> listaNecesidades = necesidadRestCliente.obtenerTodos();

        voluntariosArea.add(voluntariosFieldArea, createVirtualList(listaVoluntarios));
        necesidadesArea.add(necesidadesFieldArea, createVirtualList(listaNecesidades));

        column1.add(tituloFieldArea, descripcionFieldArea, necesidadesArea);
        column2.add(inicioFieldArea, finFieldArea, voluntariosArea);

        fields.add(column1, column2);
        getStyle().set("padding", "0 5%");
    }

    private void createCheckboxArea() {
        HorizontalLayout checkboxArea = new HorizontalLayout();
        pendienteCheckbox = new Checkbox("Dejar tarea en estado Pendiente");

        Icon infoIcon = VaadinIcon.INFO_CIRCLE_O.create();
        infoIcon.getStyle()
                .set("color", "var(--lumo-secondary-text-color)")
                .set("cursor", "pointer")
                .set("font-size", "14px")
                .set("margin-left", "0px")
                .set("align-self", "center");

        Tooltip.forComponent(infoIcon).setText("La tarea se quedará visible para voluntarios pero sin asignar.");

        checkboxArea.setAlignItems(FlexComponent.Alignment.CENTER);
        checkboxArea.add(pendienteCheckbox, infoIcon);
        add(checkboxArea);
    }

    private <T> VirtualList<T> createVirtualList(List<T> lista) {
        ComponentRenderer<Component, T> renderer = null;

        if (!lista.isEmpty()) {
            T firstElement = lista.getFirst();
            if (firstElement instanceof VoluntarioDTO) {
                renderer = (ComponentRenderer<Component, T>) voluntarioCardRenderer;
            } else if (firstElement instanceof NecesidadDTO) {
                renderer = (ComponentRenderer<Component, T>) necesidadCardRenderer;
            }
        }

        if (renderer == null) {
            throw new IllegalArgumentException("No se pudo determinar el renderer para el tipo proporcionado.");
        }

        VirtualList<T> virtualList = new VirtualList<>();
        virtualList.setItems(lista);
        virtualList.setRenderer(renderer);
        return virtualList;
    }

    private ComponentRenderer<Component, VoluntarioDTO> voluntarioCardRenderer = new ComponentRenderer<>(
            voluntario -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Avatar avatar = new Avatar(voluntario.getNombre());
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.add(new Span(voluntario.getNombre())); // Nombre del voluntario
                infoLayout.add(new Span("Email: " + voluntario.getEmail())); // Email

                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });

    private final ComponentRenderer<Component, NecesidadDTO> necesidadCardRenderer = new ComponentRenderer<>(
            necesidad -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Avatar avatar = new Avatar(necesidad.getNombre());
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.add(new Span(necesidad.getNombre())); // Nombre del voluntario
                infoLayout.add(new Span(necesidad.getDescripcion())); // Email

                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });

    private void createButton() {
        VerticalLayout buttonArea = new VerticalLayout();
        //buttonArea.getStyle().set("border", "1px solid red");
        add(buttonArea);
        Button aceptarButton = new Button("Crear");
        aceptarButton.getStyle().setBackgroundColor("#B64040");
        aceptarButton.getStyle().set("color", "white");
        aceptarButton.getStyle().set("cursor", "pointer");
        buttonArea.add(aceptarButton);
        buttonArea.setAlignSelf(Alignment.CENTER, aceptarButton);
        aceptarButton.addClickListener(e -> {
            if (tituloField.isEmpty() || descripcionField.isEmpty()
                    || inicioPicker.isEmpty() || finPicker.isEmpty()) {

                Notification.show("Por favor, rellena todos los campos obligatorios.", 3000, Notification.Position.MIDDLE);
                return;
            }

            Notification.show("Tarea creada");
        });
    }
}
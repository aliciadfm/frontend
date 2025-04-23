package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("SH - Crear tareas")
@Route(value="createTask", layout = MainLayout.class)
public class CreateTaskView extends VerticalLayout {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;
    @Autowired
    private NecesidadRestCliente necesidadRestCliente;
    @Autowired
    private TareaRestCliente tareaRestCliente;

    List<VoluntarioDTO> listaVoluntarios;
    List<NecesidadDTO> listaNecesidades;
    private VirtualList<VoluntarioDTO> virtualVoluntarios;
    private VirtualList<NecesidadDTO> virtualNecesidades;

    private TextField tituloField;
    private TextField descripcionField;
    private TextField puntoEncuentro;

    private DatePicker inicioPicker;
    private DatePicker finPicker;
    private TimePicker horaEncuentroPicker;
    private Checkbox pendienteCheckbox;

    public CreateTaskView(VoluntarioRestCliente voluntarioRestCliente, NecesidadRestCliente necesidadRestCliente, TareaRestCliente tareaRestCliente) {
        this.voluntarioRestCliente = voluntarioRestCliente;
        this.necesidadRestCliente = necesidadRestCliente;
        this.tareaRestCliente = tareaRestCliente;
        setSpacing(false);
        setPadding(false);
        createHeader();
        createTextFields();
        createCheckboxArea();
        createButton();
        tituloField.setValue(Optional.ofNullable(TaskFormData.getTitulo()).orElse(""));
        descripcionField.setValue(Optional.ofNullable(TaskFormData.getDescripcion()).orElse(""));
        if (TaskFormData.getFechaInicio() != null) {
            inicioPicker.setValue(TaskFormData.getFechaInicio());
        }
        if (TaskFormData.getFechaFin() != null) {
            finPicker.setValue(TaskFormData.getFechaFin());
        }
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
        puntoEncuentro = new TextField("");
        inicioPicker = new DatePicker("");
        finPicker = new DatePicker("");
        horaEncuentroPicker = new TimePicker("");

        //COLUMNA DE LA IZQUIERDA
        VerticalLayout column1 = new VerticalLayout();
        //column1.getStyle().set("border", "1px solid red");
        HorizontalLayout tituloFieldArea = createFieldArea("Título", tituloField);
        HorizontalLayout descripcionFieldArea = createFieldArea("Descripción", descripcionField);
        HorizontalLayout puntoEncuentroArea = createFieldArea("Punto de encuentro", puntoEncuentro);
        DateTimePicker inicio = new DateTimePicker("");
        inicio.setStep(Duration.ofMinutes(30));
        HorizontalLayout inicioFieldArea = createFieldArea("Inicio", inicioPicker);

        DateTimePicker fin = new DateTimePicker("");
        fin.setStep(Duration.ofMinutes(30));
        HorizontalLayout finFieldArea = createFieldArea("Fin", finPicker);

        // Componente para "Turno de trabajo"
        VerticalLayout turnoLabelArea = new VerticalLayout();
        turnoLabelArea.setSpacing(false);
        turnoLabelArea.setPadding(false);
        turnoLabelArea.setWidth("50%");
        Span turnoLabel = new Span("Turno de trabajo");
        turnoLabelArea.add(turnoLabel);
        turnoLabelArea.setAlignSelf(Alignment.END, turnoLabel);
        turnoLabelArea.setJustifyContentMode(JustifyContentMode.CENTER);
        Checkbox manana = new Checkbox("Mañana");
        Checkbox tarde = new Checkbox("Tarde");
        HorizontalLayout turnoArea = new HorizontalLayout();
        turnoArea.setSpacing(false);
        VerticalLayout turnoCheckBoxArea = new VerticalLayout();
        turnoCheckBoxArea.setSpacing(false);
        turnoCheckBoxArea.setPadding(false);
        turnoCheckBoxArea.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        turnoCheckBoxArea.add(manana, tarde);
        turnoArea.add(turnoLabelArea, turnoCheckBoxArea);

        HorizontalLayout turnoYHora = new HorizontalLayout();
        turnoYHora.setWidthFull();
        //turnoYHora.getStyle().set("border", "1px solid red");
        HorizontalLayout horaEncuentro = createFieldArea("Hora de encuentro", horaEncuentroPicker);
        turnoYHora.add(turnoArea, horaEncuentro);
        turnoArea.setWidthFull();
        turnoCheckBoxArea.setWidthFull();
        turnoYHora.setFlexGrow(7, turnoArea);
        turnoYHora.setFlexGrow(3, horaEncuentro);

        //COLUMNA DE LA DERECHA
        VerticalLayout column2 = new VerticalLayout();

        VerticalLayout voluntariosArea = new VerticalLayout();
        voluntariosArea.setSpacing(false);
        voluntariosArea.setPadding(false);

        VerticalLayout necesidadesArea = new VerticalLayout();
        necesidadesArea.setSpacing(false);
        necesidadesArea.setPadding(false);

        Button voluntariosButton = new Button("Añadir voluntarios");
        Button necesidadesButton = new Button("Añadir necesidades");
        voluntariosButton.addClickListener(e -> {
            saveData();
            UI.getCurrent().navigate("voluntarios");
        });
        necesidadesButton.addClickListener(e -> {
            saveData();
            listaVoluntarios.clear();
            virtualVoluntarios.setItems(listaVoluntarios);
            if (!listaNecesidades.isEmpty()) {
                long categoriaId = listaNecesidades.getFirst().getIdCategoria();
                UI.getCurrent().navigate(SelectMoreNeeds.class, categoriaId);
            } else {
                UI.getCurrent().navigate("SelectMoreNeeds");
            }
        });

        HorizontalLayout voluntariosFieldArea = createFieldArea("Voluntarios", voluntariosButton);
        HorizontalLayout necesidadesFieldArea = createFieldArea("Necesidades", necesidadesButton);

        listaVoluntarios = TaskFormData.getVoluntariosSeleccionados();
        listaNecesidades = TaskFormData.getNecesidadesSeleccionadas();

        virtualVoluntarios = createVirtualList(listaVoluntarios);
        virtualNecesidades = createVirtualList(listaNecesidades);

        voluntariosArea.add(voluntariosFieldArea, virtualVoluntarios);
        necesidadesArea.add(necesidadesFieldArea, virtualNecesidades);

        column1.add(tituloFieldArea, descripcionFieldArea, puntoEncuentroArea, necesidadesArea);
        column2.add(inicioFieldArea, finFieldArea, turnoYHora, voluntariosArea);

        fields.add(column1, column2);
        getStyle().set("padding", "0 5%");
    }

    private void saveData() {
        TaskFormData.setTitulo(tituloField.getValue());
        TaskFormData.setDescripcion(descripcionField.getValue());
        TaskFormData.setFechaInicio(inicioPicker.getValue());
        TaskFormData.setFechaFin(finPicker.getValue());
        TaskFormData.setPuntoEncuentro(puntoEncuentro.getValue());
        TaskFormData.setHoraEncuentro(horaEncuentroPicker.getValue());
    }

    private void createCheckboxArea() {
        HorizontalLayout checkboxArea = new HorizontalLayout();
        pendienteCheckbox = new Checkbox("Dejar tarea en estado 'Pendiente' ");

        Icon infoIcon = VaadinIcon.INFO_CIRCLE_O.create();
        infoIcon.getStyle()
                .set("color", "var(--lumo-secondary-text-color)")
                .set("cursor", "pointer")
                .set("font-size", "14px")
                .set("margin-left", "0px")
                .set("align-self", "center");

        Tooltip.forComponent(infoIcon).setText("La tarea será creada y posteriormente podrá ser modificada para completar todos los campos.");

        checkboxArea.setAlignItems(FlexComponent.Alignment.CENTER);
        checkboxArea.add(pendienteCheckbox, infoIcon);
        add(checkboxArea);
    }

    private <T> VirtualList<T> createVirtualList(List<T> lista) {
        ComponentRenderer<Component, T> renderer;

        if (!lista.isEmpty()) {
            T firstElement = lista.getFirst();
            if (firstElement instanceof VoluntarioDTO) {
                renderer = (ComponentRenderer<Component, T>) voluntarioCardRenderer;
            } else if (firstElement instanceof NecesidadDTO) {
                renderer = (ComponentRenderer<Component, T>) necesidadCardRenderer;
            } else {
                throw new IllegalArgumentException("Tipo de elemento no compatible con ningún renderer.");
            }
        } else {
            // Usamos el tipo de la lista que esperamos para determinar el renderer por contexto
            // Este es un poco "hardcoded", pero seguro:
            if (listaVoluntarios == lista) {
                renderer = (ComponentRenderer<Component, T>) voluntarioCardRenderer;
            } else if (listaNecesidades == lista) {
                renderer = (ComponentRenderer<Component, T>) necesidadCardRenderer;
            } else {
                throw new IllegalArgumentException("No se pudo determinar el renderer para la lista vacía.");
            }
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

                StreamResource imageResource = new StreamResource(
                        voluntario.getNombre() + ".png",
                        () -> new ByteArrayInputStream(voluntario.getImagen())
                );

                Image avatar = new Image(imageResource, "Avatar");
                avatar.getStyle()
                        .set("border-radius", "50%")
                        .set("object-fit", "cover")
                        .set("border", "2px solid #ccc");
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.setWidth("30%");
                infoLayout.add(new Span(voluntario.getNombre()));
                infoLayout.add(new Span("Email: " + voluntario.getEmail()));

                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });

    private final ComponentRenderer<Component, NecesidadDTO> necesidadCardRenderer = new ComponentRenderer<>(
            necesidad -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                StreamResource imageResource = new StreamResource(
                        necesidad.getNombre() + ".png",
                        () -> new ByteArrayInputStream(necesidad.getImagen())
                );

                Image avatar = new Image(imageResource, "Avatar");
                avatar.getStyle()
                        .set("border-radius", "50%")
                        .set("object-fit", "cover")
                        .set("border", "2px solid #ccc");
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
            LocalDate fechaInicio = inicioPicker.getValue();
            LocalDate fechaFin = finPicker.getValue();
            if (!pendienteCheckbox.getValue()) {
                if (tituloField.isEmpty() || descripcionField.isEmpty()
                        || inicioPicker.isEmpty() || finPicker.isEmpty()
                        || listaVoluntarios.isEmpty() || listaNecesidades.isEmpty()) {
                    Notification.show("Por favor, rellena todos los campos obligatorios.", 3000, Notification.Position.MIDDLE);
                    return;
                }
            }

            if(pendienteCheckbox.getValue()) {
                if(tituloField.isEmpty() || descripcionField.isEmpty()
                        || inicioPicker.isEmpty() || finPicker.isEmpty()
                        || listaNecesidades.isEmpty()) {
                    Notification.show("Por favor, rellena todos los campos obligatorios.", 3000, Notification.Position.MIDDLE);
                    return;
                }
            }

            if(fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
                Notification.show("La fecha de inicio no puede ser posterior a la fecha de fin.", 3000, Notification.Position.MIDDLE);
                return;
            }

            List<Long> idsVoluntarios = new ArrayList<>();
            List<Long> idsNecesidades = new ArrayList<>();

            for(VoluntarioDTO voluntario : listaVoluntarios) {
                idsVoluntarios.add(voluntario.getIdVoluntario());
            }

            for(NecesidadDTO necesidad : listaNecesidades) {
                idsNecesidades.add(necesidad.getIdNecesidad());
            }

            //TareaDTO tarea = new TareaDTO(null, tituloField.getValue(), descripcionField.getValue(),
                    //fechaInicio, fechaFin, horaInicio, horaFin, false, idsVoluntarios, idsNecesidades);
            for(VoluntarioDTO voluntario : listaVoluntarios) {
                System.out.println("Voluntario: " + voluntario.getIdVoluntario());
            }
            for(NecesidadDTO necesidad : listaNecesidades) {
                System.out.println("Necesidad: " + necesidad.getIdNecesidad());
            }
            //tareaRestCliente.crear(tarea);
            Notification.show("Tarea creada");
            clearForm();
        });
    }

    private void clearForm() {
        tituloField.clear();
        descripcionField.clear();
        inicioPicker.clear();
        finPicker.clear();
        puntoEncuentro.clear();
        horaEncuentroPicker.clear();

        listaVoluntarios = new java.util.ArrayList<>();
        listaNecesidades = new java.util.ArrayList<>();

        virtualVoluntarios.setItems(listaVoluntarios);
        virtualNecesidades.setItems(listaNecesidades);
    }
}
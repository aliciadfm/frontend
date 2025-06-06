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
import org.springframework.cglib.core.Local;
import salsisa.tareas.frontend.components.MainLayout;
import salsisa.tareas.frontend.components.TaskFormData;
import salsisa.tareas.frontend.components.TaskUtils;
import salsisa.tareas.frontend.dto.*;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    List<VoluntarioListadoDTO> listaVoluntarios;
    List<NecesidadDTO> listaNecesidades;
    private VirtualList<VoluntarioListadoDTO> virtualVoluntarios;
    private VirtualList<NecesidadDTO> virtualNecesidades;

    private TextField tituloField;
    private TextField descripcionField;
    private TextField puntoEncuentro;

    private DatePicker inicioPicker;
    private DatePicker finPicker;
    private TimePicker horaEncuentroPicker;
    private Checkbox manana;
    private Checkbox tarde;
    private Checkbox pendienteCheckbox;

    public CreateTaskView(VoluntarioRestCliente voluntarioRestCliente, NecesidadRestCliente necesidadRestCliente,
                          TareaRestCliente tareaRestCliente) {
        this.voluntarioRestCliente = voluntarioRestCliente;
        this.necesidadRestCliente = necesidadRestCliente;
        this.tareaRestCliente = tareaRestCliente;
        if(UI.getCurrent() != null) {
            UI.getCurrent().setLocale(Locale.forLanguageTag("es-ES"));
        }
        this.listaVoluntarios = TaskFormData.getVoluntariosSeleccionados();

        this.listaNecesidades = TaskFormData.getNecesidadesSeleccionadas();

        setSpacing(false);
        setPadding(false);
        createHeader();
        createTextFields();
        createCheckboxArea();
        tituloField.setValue(Optional.ofNullable(TaskFormData.getTitulo()).orElse(""));
        descripcionField.setValue(Optional.ofNullable(TaskFormData.getDescripcion()).orElse(""));
        if (TaskFormData.getFechaInicio() != null) {
            inicioPicker.setValue(TaskFormData.getFechaInicio());
        }
        if (TaskFormData.getFechaFin() != null) {
            finPicker.setValue(TaskFormData.getFechaFin());
        }
        puntoEncuentro.setValue(Optional.ofNullable(TaskFormData.getPuntoEncuentro()).orElse(""));
        manana.setValue(Optional.ofNullable(TaskFormData.getTurnoManana()).orElse(false));
        tarde.setValue(Optional.ofNullable(TaskFormData.getTurnoTarde()).orElse(false));
        horaEncuentroPicker.setValue(TaskFormData.getHoraEncuentro());

        createButton();
    }

    private void createHeader() {
        VerticalLayout headingDiv = new VerticalLayout();
        headingDiv.setWidth("100%");
        H1 title = new H1("Crear tarea");
        headingDiv.add(title);
        add(headingDiv);
    }

    private HorizontalLayout createFieldArea(String label, Component field) {
        HorizontalLayout area = new HorizontalLayout();
        area.setWidth("100%");
        VerticalLayout labelArea = new VerticalLayout();
        labelArea.setSpacing(false);
        labelArea.setPadding(false);
        labelArea.setWidth("15%");
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
        tituloField.setMaxLength(50);
        descripcionField = new TextField("");
        descripcionField.setMaxLength(150);
        puntoEncuentro = new TextField("");
        puntoEncuentro.setMaxLength(75);
        inicioPicker = new DatePicker("");
        inicioPicker.setMin(LocalDate.now());
        inicioPicker.setLocale(Locale.forLanguageTag("es-ES"));
        finPicker = new DatePicker("");
        finPicker.setMin(LocalDate.now());
        finPicker.setLocale(Locale.forLanguageTag("es-ES"));
        horaEncuentroPicker = new TimePicker("");

        //COLUMNA DE LA IZQUIERDA
        VerticalLayout column1 = new VerticalLayout();
        HorizontalLayout tituloFieldArea = createFieldArea("Título", tituloField);
        HorizontalLayout descripcionFieldArea = createFieldArea("Descripción", descripcionField);
        HorizontalLayout puntoEncuentroArea = createFieldArea("Punto encuentro", puntoEncuentro);
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
        manana = new Checkbox("Mañana");
        tarde = new Checkbox("Tarde");
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
                UI.getCurrent().navigate("SelectMoreNeeds/createTask/" + categoriaId);
            } else {
                UI.getCurrent().navigate("SelectMoreNeeds/createTask");
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
        TaskFormData.setTurnoManana(manana.getValue());
        TaskFormData.setTurnoTarde(tarde.getValue());
        TaskFormData.setVoluntariosSeleccionados(listaVoluntarios);
        TaskFormData.setNecesidadesSeleccionadas(listaNecesidades);
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
            if (firstElement instanceof VoluntarioListadoDTO) {
                renderer = (ComponentRenderer<Component, T>) voluntarioCardRenderer;
            } else if (firstElement instanceof NecesidadDTO) {
                renderer = (ComponentRenderer<Component, T>) necesidadCardRenderer;
            } else {
                throw new IllegalArgumentException("Tipo de elemento no compatible con ningún renderer.");
            }
        } else {
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


    private ComponentRenderer<Component, VoluntarioListadoDTO> voluntarioCardRenderer = new ComponentRenderer<>(
            voluntario -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                byte[] imagen = voluntario.getImagen();
                if (imagen == null || imagen.length == 0) {
                    imagen = getDefaultImage();
                }

                Image avatar;

                if (imagen != null && imagen.length > 0) {
                    StreamResource imageResource = new StreamResource(
                            voluntario.getNombre() + ".png",
                            () -> new ByteArrayInputStream(voluntario.getImagen())
                    );
                    avatar = new Image(imageResource, "Avatar");
                } else {
                    avatar = new Image("icons/defaultuserimage.png", "Avatar");
                }
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

    private byte[] getDefaultImage() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("defaultuserimage.png")) {
            return is != null ? is.readAllBytes() : new byte[0];
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private final ComponentRenderer<Component, NecesidadDTO> necesidadCardRenderer = new ComponentRenderer<>(
            necesidad -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                byte[] imagen = necesidad.getImagen();
                if (imagen == null || imagen.length == 0) {
                    imagen = getDefaultImage();
                }

                Image avatar;

                if (imagen != null && imagen.length > 0) {
                    StreamResource imageResource = new StreamResource(
                            necesidad.getNombre() + ".png",
                            () -> new ByteArrayInputStream(necesidad.getImagen())
                    );
                    avatar = new Image(imageResource, "Avatar");
                } else {
                    avatar = new Image("icons/defaultneedimage.png", "Avatar");
                }
                avatar.getStyle()
                        .set("border-radius", "50%")
                        .set("object-fit", "cover")
                        .set("border", "2px solid #ccc");
                avatar.setHeight("64px");
                avatar.setWidth("64px");

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.add(new Span(necesidad.getNombre()));
                infoLayout.add(new Span(necesidad.getDescripcion()));

                cardLayout.add(avatar, infoLayout);
                return cardLayout;
            });

    private void createButton() {
        VerticalLayout buttonArea = new VerticalLayout();
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

            for(VoluntarioListadoDTO voluntario : listaVoluntarios) {
                idsVoluntarios.add(voluntario.getId());
            }

            for(NecesidadDTO necesidad : listaNecesidades) {
                idsNecesidades.add(necesidad.getIdNecesidad());
            }

            if(TaskUtils.validateFields(tituloField.getValue(), descripcionField.getValue(),
                    puntoEncuentro.getValue(), inicioPicker.getValue(), finPicker.getValue())) {
                TareaDTO tarea = new TareaDTO(null, tituloField.getValue(), descripcionField.getValue(),
                        fechaInicio, fechaFin, horaEncuentroPicker.getValue(), puntoEncuentro.getValue(),
                        manana.getValue(), tarde.getValue(),
                        pendienteCheckbox.getValue() ? Estado.PENDIENTE : Estado.ASIGNADA,
                        idsVoluntarios, idsNecesidades);

                tareaRestCliente.crear(tarea);
                Notification.show("Tarea creada");
                clearForm();
            }
        });
    }

    boolean validateFields(String titulo, String descripcion, String punto, LocalDate inicio, LocalDate fin) {
        return !titulo.isEmpty() && !descripcion.isEmpty() && !punto.isEmpty() &&
                inicio != null && fin != null &&
                !fin.isBefore(inicio) && !inicio.isBefore(LocalDate.now());
    }

    private void clearForm() {
        tituloField.clear();
        descripcionField.clear();
        inicioPicker.clear();
        finPicker.clear();
        puntoEncuentro.clear();
        horaEncuentroPicker.clear();
        listaVoluntarios.clear();
        listaNecesidades.clear();
        virtualVoluntarios.setItems(listaVoluntarios);
        virtualNecesidades.setItems(listaNecesidades);
        manana.setValue(false);
        tarde.setValue(false);
    }
}
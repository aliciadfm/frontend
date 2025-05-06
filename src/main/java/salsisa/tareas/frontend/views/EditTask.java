package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Editar tarea")
@Route(value="editTask", layout = MainLayout.class)
public class EditTask extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    private VoluntarioRestCliente voluntarioRestCliente;
    @Autowired
    private NecesidadRestCliente necesidadRestCliente;
    @Autowired
    private TareaRestCliente tareaRestCliente;

    // Column1
    private TextField tituloField;
    private TextField descripcionField;
    private TextField puntoEncuentro;
    // Column2
    private DatePicker inicioPicker;
    private DatePicker finPicker;
    private TimePicker horaEncuentroPicker;
    private Checkbox manana;
    private Checkbox tarde;
    private Checkbox pendienteCheckbox;

    private List<VoluntarioDTO> listaVoluntarios;
    private List<NecesidadDTO> listaNecesidades;
    private VirtualList<VoluntarioDTO> virtualVoluntarios;
    private VirtualList<NecesidadDTO> virtualNecesidades;

    List<Long> idsVoluntariosID;
    List<Long> idsNecesidadesID;

    TareaDTO tarea;

    @Autowired
    public EditTask(VoluntarioRestCliente voluntarioRestCliente, NecesidadRestCliente necesidadRestCliente,
                    TareaRestCliente tareaRestCliente) {
        this.voluntarioRestCliente = voluntarioRestCliente;
        this.necesidadRestCliente = necesidadRestCliente;
        this.tareaRestCliente = tareaRestCliente;

        this.idsVoluntariosID = new ArrayList<>();
        this.idsNecesidadesID = new ArrayList<>();

        this.listaVoluntarios = new ArrayList<>();
        this.listaNecesidades = new ArrayList<>();

        this.virtualVoluntarios = new VirtualList<>();
        this.virtualNecesidades = new VirtualList<>();

        if(!TaskFormData.getVoluntariosSeleccionados().isEmpty()) {
            listaVoluntarios.addAll(TaskFormData.getVoluntariosSeleccionados());
        }
        if(!TaskFormData.getNecesidadesSeleccionadas().isEmpty()) {
            listaNecesidades.addAll(TaskFormData.getNecesidadesSeleccionadas());
        }

        setSpacing(false);
        setPadding(false);
        getStyle().set("padding", "0 5%");
        createHeader();

        System.out.println("Lista de voluntarios: " + listaVoluntarios);
        System.out.println("Lista de necesidades: " + listaNecesidades);
    }

    public void createHeader() {
        VerticalLayout header = new VerticalLayout();
        VerticalLayout subHeader = new VerticalLayout();
        H1 title = new H1("Editar tarea");
        header.add(title);

        Span info = new Span("Modifique solo los valores que quiera cambiar. Los campos en blanco " +
                "conservarán su valor anterior.");
        subHeader.add(info);

        add(header, subHeader);
    }

    public void createCentralContainer(boolean borders) {
        HorizontalLayout fieldsArea = new HorizontalLayout();
        fieldsArea.setSpacing(false);
        fieldsArea.setPadding(false);
        fieldsArea.setWidth("100%");
        add(fieldsArea);

        tituloField = new TextField("");
        descripcionField = new TextField("");
        puntoEncuentro = new TextField("");
        inicioPicker = new DatePicker("");
        inicioPicker.setMin(LocalDate.now());
        finPicker = new DatePicker("");
        finPicker.setMin(LocalDate.now());
        horaEncuentroPicker = new TimePicker("");
        manana = new Checkbox("Mañana");
        manana.setValue(tarea.getTurnoManana());
        tarde = new Checkbox("Tarde");
        tarde.setValue(tarea.getTurnoTarde());


        Button voluntariosButton = new Button("Añadir voluntarios");
        Button necesidadesButton = new Button("Añadir necesidades");

        if(tarea.getEstado() == Estado.ASIGNADA || tarea.getEstado() == Estado.ENPROCESO ) {
            tituloField.setReadOnly(true);
            descripcionField.setReadOnly(true);
            inicioPicker.setReadOnly(true);
            finPicker.setReadOnly(true);
            necesidadesButton.setEnabled(false);
            tarde.setReadOnly(true);
            manana.setReadOnly(true);
        }

        if(tarea.getEstado() == Estado.TERMINADA) {
            tituloField.setReadOnly(true);
            descripcionField.setReadOnly(true);
            puntoEncuentro.setReadOnly(true);
            horaEncuentroPicker.setReadOnly(true);
            inicioPicker.setReadOnly(true);
            finPicker.setReadOnly(true);
            necesidadesButton.setEnabled(false);
            voluntariosButton.setEnabled(false);
        }

        tituloField.setPlaceholder(tarea.getTitulo());
        descripcionField.setPlaceholder(tarea.getDescripcion());
        puntoEncuentro.setPlaceholder(tarea.getPuntoEncuentro());
        if(tarea.getFechaInicio() != null) {
            inicioPicker.setPlaceholder(tarea.getFechaInicio().toString());
        }
        if(tarea.getFechaFin() != null) {
            finPicker.setPlaceholder(tarea.getFechaFin().toString());
        }
        if(tarea.getHoraEncuentro() != null) {
            horaEncuentroPicker.setPlaceholder(tarea.getHoraEncuentro().toString());
        }

        VerticalLayout column1 = new VerticalLayout();
        VerticalLayout column2 = new VerticalLayout();
        fieldsArea.add(column1, column2);
        // Column1
        HorizontalLayout tituloFieldArea = createFieldArea("Titulo", tituloField);
        HorizontalLayout descripcionFieldArea = createFieldArea("Descripcion", descripcionField);
        HorizontalLayout puntoEncuentroArea = createFieldArea("Punto encuentro", puntoEncuentro);
        // Column2
        HorizontalLayout inicioPickerArea = createFieldArea("Inicio", inicioPicker);
        HorizontalLayout finPickerArea = createFieldArea("Fin", finPicker);
        HorizontalLayout turnoYHora = createHoraYTurnoArea(borders);

        VerticalLayout voluntariosArea = new VerticalLayout();
        voluntariosArea.setSpacing(false);
        voluntariosArea.setPadding(false);

        VerticalLayout necesidadesArea = new VerticalLayout();
        necesidadesArea.setSpacing(false);
        necesidadesArea.setPadding(false);

        voluntariosButton.addClickListener(e -> {
            saveData();
            UI.getCurrent().navigate("voluntarios/edit-task/" + tarea.getIdTarea());
        });
        necesidadesButton.addClickListener(e -> {
            saveData();
            listaVoluntarios.clear();
            virtualVoluntarios.setItems(listaVoluntarios);
            if (!listaNecesidades.isEmpty()) {
                long categoriaId = listaNecesidades.getFirst().getIdCategoria();
                UI.getCurrent().navigate("SelectMoreNeeds/editTask/" + categoriaId + "/" + tarea.getIdTarea());
            } else {
                UI.getCurrent().navigate("SelectMoreNeeds");
            }
        });

        HorizontalLayout voluntariosFieldArea = createFieldArea("Voluntarios", voluntariosButton);
        HorizontalLayout necesidadesFieldArea = createFieldArea("Necesidades", necesidadesButton);

        List<Long> listaVoluntariosID = tarea.getIdsVoluntarios();
        if (listaVoluntariosID != null) {
            for(Long idVol : listaVoluntariosID) {
                VoluntarioDTO voluntario = voluntarioRestCliente.obtenerPorId(idVol);
                listaVoluntarios.add(voluntario);
            }
        }

        List<Long> listaNecesidadesID = tarea.getIdsNecesidades();
        if (listaNecesidadesID != null) {
            for(Long idNec : listaNecesidadesID) {
                NecesidadDTO necesidad = necesidadRestCliente.obtenerPorId(idNec);
                listaNecesidades.add(necesidad);
            }
        }

        virtualVoluntarios = createVirtualList(listaVoluntarios);
        virtualNecesidades = createVirtualList(listaNecesidades);

        voluntariosArea.add(voluntariosFieldArea, virtualVoluntarios);
        necesidadesArea.add(necesidadesFieldArea, virtualNecesidades);

        column1.add(tituloFieldArea, descripcionFieldArea, puntoEncuentroArea, necesidadesArea);
        column2.add(inicioPickerArea, finPickerArea, turnoYHora, voluntariosArea);

        if(borders) {
            fieldsArea.getStyle().set("border", "1px solid red");
            column1.getStyle().set("border", "1px solid red");
            column2.getStyle().set("border", "1px solid red");
            tituloFieldArea.getStyle().set("border", "1px solid red");
            descripcionFieldArea.getStyle().set("border", "1px solid red");
            puntoEncuentroArea.getStyle().set("border", "1px solid red");
            inicioPickerArea.getStyle().set("border", "1px solid red");
            finPickerArea.getStyle().set("border", "1px solid red");
            turnoYHora.getStyle().set("border", "1px solid red");
        }
    }

    private HorizontalLayout createHoraYTurnoArea(boolean borders) {
        HorizontalLayout turnoYHora = new HorizontalLayout();
        Span turno = new Span("Turno");
        Span hora = new Span("Hora de encuentro");
        turnoYHora.setWidth("100%");
        turnoYHora.setHeight("100%");
        turnoYHora.setPadding(false);
        turnoYHora.setSpacing(false);
        HorizontalLayout turnoArea = new HorizontalLayout();
        HorizontalLayout turnoLabelArea = new HorizontalLayout();
        VerticalLayout turnoCheckBoxes = new VerticalLayout();
        HorizontalLayout mananaCheckBoxArea = new HorizontalLayout();
        HorizontalLayout tardeCheckBoxArea = new HorizontalLayout();
        HorizontalLayout horaArea = new HorizontalLayout();
        HorizontalLayout labelArea = new HorizontalLayout();
        HorizontalLayout horaPickerArea = new HorizontalLayout();
        turnoArea.setWidth("50%");
        turnoLabelArea.setWidth("40%");
        turnoLabelArea.setSpacing(false);
        turnoLabelArea.getStyle().set("padding", "0 5%");
        turnoLabelArea.setAlignSelf(Alignment.CENTER, turno);
        turnoLabelArea.setJustifyContentMode(JustifyContentMode.END);
        turnoCheckBoxes.setPadding(false);
        turnoCheckBoxes.getStyle().set("gap", "13%");
        turnoYHora.add(turnoArea, horaArea);
        turnoArea.add(turnoLabelArea, turnoCheckBoxes);
        turnoCheckBoxes.add(mananaCheckBoxArea, tardeCheckBoxArea);
        turnoLabelArea.add(turno);

        horaArea.add(labelArea, horaPickerArea);
        horaArea.setPadding(false);
        horaArea.setSpacing(false);
        horaArea.setWidth("50%");
        labelArea.setWidth("40%");
        labelArea.add(hora);
        labelArea.setAlignSelf(Alignment.CENTER, hora);
        labelArea.setJustifyContentMode(JustifyContentMode.END);
        labelArea.getStyle().set("padding", "0 5%");
        horaPickerArea.setWidth("60%");
        horaPickerArea.setAlignSelf(Alignment.CENTER, horaEncuentroPicker);
        horaEncuentroPicker.setWidth("93%");
        horaPickerArea.add(horaEncuentroPicker);
        mananaCheckBoxArea.add(manana);
        tardeCheckBoxArea.add(tarde);

        if(borders) {
            turnoArea.getStyle().set("border", "1px solid red");
            turnoCheckBoxes.getStyle().set("border", "1px solid red");
            mananaCheckBoxArea.getStyle().set("border", "1px solid red");
            tardeCheckBoxArea.getStyle().set("border", "1px solid red");
            horaArea.getStyle().set("border", "1px solid red");
            labelArea.getStyle().set("border", "1px solid red");
            horaPickerArea.getStyle().set("border", "1px solid red");
            turnoLabelArea.getStyle().set("border", "1px solid red");
        }
        return turnoYHora;
    }

    private void createButton() {
        VerticalLayout buttonArea = new VerticalLayout();
        add(buttonArea);
        Button editarButton = new Button("Editar");
        editarButton.getStyle().setBackgroundColor("#B64040");
        editarButton.getStyle().set("color", "white");
        editarButton.getStyle().set("cursor", "pointer");
        buttonArea.add(editarButton);
        buttonArea.setAlignSelf(Alignment.CENTER, editarButton);

        editarButton.addClickListener(e -> {
            if(!tituloField.getValue().isEmpty()) tarea.setTitulo(tituloField.getValue());
            if(!descripcionField.getValue().isEmpty()) tarea.setDescripcion(descripcionField.getValue());
            if(!puntoEncuentro.getValue().isEmpty()) tarea.setPuntoEncuentro(puntoEncuentro.getValue());
            if(inicioPicker.getValue() != null) tarea.setFechaInicio(inicioPicker.getValue());
            if(finPicker.getValue() != null) tarea.setFechaFin(finPicker.getValue());
            tarea.setTurnoManana(manana.getValue());
            tarea.setTurnoTarde(tarde.getValue());
            tareaRestCliente.actualizar(tarea.getIdTarea(), tarea);
            Notification.show("Tarea actualizada correctamente");
            UI.getCurrent().navigate("tareas");
        });
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
                        () -> {
                            byte[] imagen = voluntario.getImagen();
                            if (imagen == null) {
                                // Cargar la imagen predeterminada si no existe imagen del voluntario
                                return new ByteArrayInputStream(getDefaultImage());
                            }
                            return new ByteArrayInputStream(imagen);
                        }
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

    private byte[] getDefaultImage() {
        // Aquí debes cargar la imagen predeterminada como un byte[] (puede estar en resources, por ejemplo)
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("defaultuserimage.png")) {
            return is != null ? is.readAllBytes() : new byte[0];
        } catch (IOException e) {
            // Si no se encuentra la imagen, puedes devolver un array vacío o alguna imagen por defecto.
            return new byte[0];
        }
    }

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
        System.out.println("Voluntarios guardados: " + listaVoluntarios);
        TaskFormData.setNecesidadesSeleccionadas(listaNecesidades);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long tareaId) {
        if (tareaId == null) {
            Notification.show("No se ha proporcionado una tarea.");
            return;
        }
        tarea = tareaRestCliente.obtenerPorId(tareaId);
        if (tarea == null) {
            Notification.show("Tarea no encontrada.");
            return;
        }
        createCentralContainer(false);

        if(!listaVoluntarios.isEmpty()) {
            virtualVoluntarios.setItems(listaVoluntarios);
        }

        createButton();
    }
}

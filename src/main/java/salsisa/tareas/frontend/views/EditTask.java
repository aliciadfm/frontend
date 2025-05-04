package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Editar tarea")
@Route(value="editTask", layout = MainLayout.class)
public class EditTask extends VerticalLayout {

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

    public EditTask() {
        setSpacing(false);
        setPadding(false);
        getStyle().set("padding", "0 5%");
        createHeader();
        createCentralContainer(false);
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
        finPicker = new DatePicker("");
        horaEncuentroPicker = new TimePicker("");
        manana = new Checkbox("Mañana");
        tarde = new Checkbox("Tarde");

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

        column1.add(tituloFieldArea, descripcionFieldArea, puntoEncuentroArea);
        column2.add(inicioPickerArea, finPickerArea, turnoYHora);

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
}

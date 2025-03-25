package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;

@PageTitle("SH - Crear tareas")
@Route(value="createTask", layout = MainLayout.class)
public class CreateTaskView extends VerticalLayout {
    public CreateTaskView() {
        setSpacing(false);
        setPadding(false);
        createHeader();
        createTextFields();
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
        //fields.getStyle().set("border", "1px solid red");
        fields.setWidth("100%");

        //COLUMNA DE LA IZQUIERDA
        VerticalLayout column1 = new VerticalLayout();
        //column1.getStyle().set("border", "1px solid red");
        HorizontalLayout tituloFieldArea = createFieldArea("Título", new TextField(""));
        HorizontalLayout descripcionFieldArea = createFieldArea("Descripción", new TextField(""));
        HorizontalLayout necesidadesFieldArea = createFieldArea("Necesidades", new TextField(""));
        column1.add(tituloFieldArea, descripcionFieldArea, necesidadesFieldArea);

        //COLUMNA DE LA DERECHA
        VerticalLayout column2 = new VerticalLayout();
        //column2.getStyle().set("border", "1px solid red");
        HorizontalLayout fechaFieldArea = createFieldArea("Fecha", new DatePicker(""));
        HorizontalLayout duracionFieldArea = createFieldArea("Duración", new TextField(""));
        HorizontalLayout voluntariosFieldArea = createFieldArea("Voluntarios", new TextField(""));
        column2.add(fechaFieldArea, duracionFieldArea, voluntariosFieldArea);

        fields.add(column1, column2);
        getStyle().set("padding", "0 5%");

    }

    private void createButton() {
        VerticalLayout buttonArea = new VerticalLayout();
        //buttonArea.getStyle().set("border", "1px solid red");
        add(buttonArea);
        Button aceptarButton = new Button("Crear");
        buttonArea.add(aceptarButton);
        buttonArea.setAlignSelf(Alignment.CENTER, aceptarButton);
    }
}

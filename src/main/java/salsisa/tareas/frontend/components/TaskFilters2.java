package salsisa.tareas.frontend.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.FiltroTareaDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskFilters2 extends VerticalLayout {

    private Checkbox mostrarTodo;
    private Checkbox finalizada;
    private Checkbox enProceso;
    private Checkbox asignada;
    private Checkbox pendiente;

    private RadioButtonGroup<String> ordenarPor;

    private DatePicker fechaInicio;
    private DatePicker fechaFin;

    private Button botonFiltrar;

    public TaskFilters2() {
        setWidth("250px");
        getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "10px")
                .set("box-shadow", "2px 2px 10px rgba(0,0,0,0.1)");

        add(new H3("Estado"));
        createEstadoFilter();
        add(mostrarTodo,finalizada,enProceso,asignada,pendiente);
        createOrdenarPorFilter();
        add(ordenarPor);
        createFechaFilters();
        add(fechaInicio,fechaFin);
        createBotonFiltrar();
        add(botonFiltrar);
    }
    private void createEstadoFilter() {

        mostrarTodo = crearCheckboxConColor("Mostrar Todo", "#e0e0e0", "#a0a0a0");
        finalizada = crearCheckboxConColor("Finalizada", "#b3ffb3", "#99e699"); // verde claro -> verde un poco más oscuro
        enProceso = crearCheckboxConColor("En Proceso", "#ffffb3", "#e6e699");   // amarillo claro -> amarillo más oscuro
        asignada = crearCheckboxConColor("Asignada", "#ffd9b3", "#e6c2a3");      // naranja claro -> más oscuro
        pendiente = crearCheckboxConColor("Pendiente", "#ffb3b3", "#e69999");    // rojo claro -> rojo más oscuro


    }

    private void createOrdenarPorFilter() {
        ordenarPor = new RadioButtonGroup<>();
        ordenarPor.setLabel("Ordenar Por");
        ordenarPor.setItems("Nombre","Estado","FechaInicio","Urgencia");
        ordenarPor.setValue("Nombre"); // Seleccionar "Nombre" por defecto
        ordenarPor.setRequired(true);  // Obliga a que siempre haya una opción seleccionada
        ordenarPor.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    }

    private Checkbox crearCheckboxConColor(String label, String colorFondo, String colorSeleccionado) {
        Checkbox checkbox = new Checkbox(label);
        checkbox.getStyle()
                .set("background-color", colorFondo)
                .set("border-radius", "5px")
                .set("padding", "5px");

        checkbox.addValueChangeListener(event -> {
            if (event.getValue()) {
                checkbox.getStyle().set("background-color", colorSeleccionado);
            } else {
                checkbox.getStyle().set("background-color", colorFondo);
            }
        });

        return checkbox;
    }
    private void createFechaFilters() {
        fechaInicio = new DatePicker("Fecha Inicio");
        fechaFin = new DatePicker("Fecha Fin");

        // Validar al cambiar la fecha de inicio
        fechaInicio.addValueChangeListener(event -> validarFechas());

        // Validar al cambiar la fecha de fin
        fechaFin.addValueChangeListener(event -> validarFechas());
    }
    private void validarFechas() {
        LocalDate inicio = fechaInicio.getValue();
        LocalDate fin = fechaFin.getValue();

        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            fechaInicio.setInvalid(true);
            fechaFin.setInvalid(true);
            fechaInicio.setErrorMessage("La fecha de inicio debe ser anterior o igual a la fecha de fin.");
            fechaFin.setErrorMessage("La fecha de fin debe ser posterior o igual a la fecha de inicio.");
        } else {
            fechaInicio.setInvalid(false);
            fechaFin.setInvalid(false);
            fechaInicio.setErrorMessage(null);
            fechaFin.setErrorMessage(null);
        }
    }
    private void createBotonFiltrar() {
        botonFiltrar = new Button("Filtrar");
        botonFiltrar.addClickListener(event -> {
            FiltroTareaDTO filtro = new FiltroTareaDTO();

            // Si "Mostrar tod" está activado, dejamos la lista como null
            if (!mostrarTodo.getValue()) {
                List<String> estados = new ArrayList<>();
                if (pendiente.getValue()) estados.add("PENDIENTE");
                if (asignada.getValue()) estados.add("ASIGNADA");
                if (enProceso.getValue()) estados.add("EN_PROCESO");
                if (finalizada.getValue()) estados.add("FINALIZADA");

                if (!estados.isEmpty()) {
                    filtro.setEstados(estados);
                }
            }

            // Fechas: solo si ambas están presentes
            LocalDate inicio = fechaInicio.getValue();
            LocalDate fin = fechaFin.getValue();
            if (inicio != null && fin != null) {
                filtro.setFechaInicio(inicio);
                filtro.setFechaFin(fin);
            }

            // Orden
            String orden = ordenarPor.getValue();
            if (orden != null) {
                filtro.setOrden(orden.toLowerCase());
            }
            //Notification.show("Filtro creado:\n" + filtro.toString(), 5000, Notification.Position.MIDDLE);
        });
    }
}

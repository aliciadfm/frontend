package salsisa.tareas.frontend.views;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskFormData {
    // Getters y setters
    @Setter
    @Getter
    private static String titulo;
    @Getter
    @Setter
    private static String descripcion;
    @Getter
    @Setter
    private static LocalDate fechaInicio;
    @Setter
    @Getter
    private static LocalDate fechaFin;

    public static void reset() {
        titulo = null;
        descripcion = null;
        fechaInicio = null;
        fechaFin = null;
    }
}


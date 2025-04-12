package salsisa.tareas.frontend.views;

import lombok.Getter;
import lombok.Setter;
import salsisa.tareas.frontend.dto.NecesidadDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    @Getter
    @Setter
    private static List<NecesidadDTO> necesidadesSeleccionadas = new ArrayList<>();

    public static void reset() {
        titulo = null;
        descripcion = null;
        fechaInicio = null;
        fechaFin = null;
        necesidadesSeleccionadas.clear();
    }
}


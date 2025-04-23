package salsisa.tareas.frontend.views;

import lombok.Getter;
import lombok.Setter;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;

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
    private static LocalTime horaEncuentro;
    @Setter
    @Getter
    private static String puntoEncuentro;
    @Getter
    @Setter
    private static Boolean turnoManana;
    @Getter
    @Setter
    private static Boolean turnoTarde;
    @Getter
    @Setter
    private static Estado estado;
    @Getter
    @Setter
    private static List<VoluntarioDTO> voluntariosSeleccionados = new ArrayList<>();
    @Getter
    @Setter
    private static List<NecesidadDTO> necesidadesSeleccionadas = new ArrayList<>();
}


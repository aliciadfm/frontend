package salsisa.tareas.frontend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class FiltroTareaDTO {
    // Lista de estados como strings ("PENDIENTE", "ASIGNADA", etc.)
    private List<String> estados;

    // Fecha de inicio del rango
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    // Fecha de fin del rango
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    // Criterio de ordenación: "nombre", "estado", "fechainicio", "urgencia"
    private String orden;
}

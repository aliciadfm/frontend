package salsisa.tareas.frontend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaResumenDTO {
    private Long idTarea;
    private String titulo;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private String puntoEncuentro;
    private Estado estado;
    private int totalAsistencias;
    private int totalAceptadas;
    private int totalCanceladas;
    private boolean activo;
}

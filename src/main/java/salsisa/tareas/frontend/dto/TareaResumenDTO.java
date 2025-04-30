package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaResumenDTO {
    private Long idTarea;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private int totalAsistencias;
    private int totalAceptadas;
    private int totalCanceladas;
}

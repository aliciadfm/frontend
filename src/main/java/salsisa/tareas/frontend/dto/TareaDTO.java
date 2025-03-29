package salsisa.tareas.frontend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {
    private Long idTarea;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private Boolean realizada;
}

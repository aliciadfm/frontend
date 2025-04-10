package salsisa.tareas.frontend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFin;
    private Boolean realizada;
}

package salsisa.tareas.frontend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private List<Long> idsVoluntarios;
    private List<Long> idsNecesidades;
}

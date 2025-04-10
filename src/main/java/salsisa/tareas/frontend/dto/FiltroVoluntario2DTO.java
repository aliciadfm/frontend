package salsisa.tareas.frontend.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroVoluntario2DTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private Long idCategoria;
    private List<Long> voluntarios;
}

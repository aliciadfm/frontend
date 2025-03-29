package salsisa.tareas.frontend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroVoluntarioDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
}

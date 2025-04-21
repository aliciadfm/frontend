package salsisa.tareas.frontend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroVoluntario1DTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean turnoManana;
    private Boolean turnoTarde;
    private Long idCategoria;
}

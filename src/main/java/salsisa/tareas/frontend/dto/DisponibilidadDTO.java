package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadDTO {
    private Long idVoluntario;
    private DiaSemana dia;
    private Boolean manana;
    private Boolean tarde;
}

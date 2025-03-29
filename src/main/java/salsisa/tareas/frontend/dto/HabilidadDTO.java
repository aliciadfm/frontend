package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadDTO {
    private Long idHabilidad;
    private String nombre;
    private String descripcion;
}

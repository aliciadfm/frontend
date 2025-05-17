package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoluntarioListadoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String apellido;
    private byte[] imagen;
}

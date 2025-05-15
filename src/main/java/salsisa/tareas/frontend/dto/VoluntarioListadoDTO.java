package salsisa.tareas.frontend.dto;

import lombok.Data;

@Data
public class VoluntarioListadoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String apellido;
    private byte[] imagen;
}

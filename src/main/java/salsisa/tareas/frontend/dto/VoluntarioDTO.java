package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioDTO {
    private Long idVoluntario;
    private String nombre;
    private String apellidos;
    private LocalDate fecha;
    private String dni;
    private String email;
    private Integer telefono;
    private String direccion;
    private String contrasena;
    private byte[] imagen;
    private String correo;
}

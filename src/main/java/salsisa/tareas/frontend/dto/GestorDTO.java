package salsisa.tareas.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GestorDTO {
    private Long idGestor;
    private String usuario;
    private String contrasena;
}

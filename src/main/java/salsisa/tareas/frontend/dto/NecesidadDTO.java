package salsisa.tareas.frontend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NecesidadDTO {
    private Long idNecesidad;
    private String nombre;
    private String descripcion;
    private boolean archivada;
    private byte[] imagen;
    private Urgencia urgencia;
    private LocalDateTime fechaCreacion;
    private Long idCategoria;
    private Long idTarea;
    private Long idAfectado;
    private Long idLocalizacion;
    private List<Long> idsHabilidades;
}

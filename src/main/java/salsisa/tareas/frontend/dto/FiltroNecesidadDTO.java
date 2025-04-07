package salsisa.tareas.frontend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroNecesidadDTO {

    private List<Urgencia> urgencias;
    private List<Long> categorias;
}

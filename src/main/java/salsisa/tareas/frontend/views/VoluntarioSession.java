package salsisa.tareas.frontend.views;

import lombok.Getter;
import lombok.Setter;
import salsisa.tareas.frontend.dto.VoluntarioDTO;

import java.util.ArrayList;
import java.util.List;

public class VoluntarioSession {
    @Getter
    @Setter
    private static List<VoluntarioDTO> voluntariosSeleccionados = new ArrayList<>();

    public static void limpiar() {
        voluntariosSeleccionados.clear();
    }
}


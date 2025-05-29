package salsisa.tareas.frontend.components;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class TaskUtils {
    public static boolean validateFields(String titulo, String descripcion, String punto, LocalDate inicio, LocalDate fin) {
        return !titulo.isEmpty() && !descripcion.isEmpty() && !punto.isEmpty() &&
                inicio != null && fin != null &&
                !fin.isBefore(inicio) && !inicio.isBefore(LocalDate.now());
    }
}

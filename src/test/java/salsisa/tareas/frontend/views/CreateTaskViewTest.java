package salsisa.tareas.frontend.views;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salsisa.tareas.frontend.dto.VoluntarioListadoDTO;
import salsisa.tareas.frontend.servicesAPI.NecesidadRestCliente;
import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import salsisa.tareas.frontend.servicesAPI.VoluntarioRestCliente;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CreateTaskViewTest {
    CreateTaskView createTaskView;
    VoluntarioRestCliente voluntarioRestCliente;
    NecesidadRestCliente necesidadRestCliente;
    TareaRestCliente tareaRestCliente;

    @BeforeEach
    void setUp() {
        createTaskView = new CreateTaskView(voluntarioRestCliente, necesidadRestCliente, tareaRestCliente);
    }

    @Test
    void testValidateFields_AllCorrect() {
        boolean result = createTaskView.validateFields("Titulo", "Descripcion", "Punto",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertTrue(result);
    }

    @Test
    void testValidateFields_EmptyTaskName() {
        boolean result = createTaskView.validateFields("", "Some description", "Zone",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_NullDate() {
        boolean result = createTaskView.validateFields("Titulo", "Some description", "Zone",
                null, LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_PastDate() {
        boolean result = createTaskView.validateFields("Titulo", "Some description", "Zone",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_FinBeforeIni() {
        boolean result = createTaskView.validateFields("Titulo", "Some description", "Zone",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(1));
        assertFalse(result);
    }
}
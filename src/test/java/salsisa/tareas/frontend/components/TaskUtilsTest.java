package salsisa.tareas.frontend.components;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskUtilsTest {
    @Test
    void testValidateFields_AllCorrect() {
        boolean result = TaskUtils.validateFields("Titulo", "Descripcion", "Punto",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertTrue(result);
    }

    @Test
    void testValidateFields_EmptyTaskName() {
        boolean result = TaskUtils.validateFields("", "Some description", "Zone",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_NullDate() {
        boolean result = TaskUtils.validateFields("Titulo", "Some description", "Zone",
                null, LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_PastDate() {
        boolean result = TaskUtils.validateFields("Titulo", "Some description", "Zone",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));
        assertFalse(result);
    }

    @Test
    void testValidateFields_FinBeforeIni() {
        boolean result = TaskUtils.validateFields("Titulo", "Some description", "Zone",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(1));
        assertFalse(result);
    }
}
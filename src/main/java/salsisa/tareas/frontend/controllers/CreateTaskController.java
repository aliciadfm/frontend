package salsisa.tareas.frontend.controllers;

import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;
import salsisa.tareas.frontend.views.TaskFormData;

public class CreateTaskController {
    private final TareaRestCliente tareaRestCliente;

    public CreateTaskController(TareaRestCliente tareaRestCliente) {
        this.tareaRestCliente = tareaRestCliente;
    }
}

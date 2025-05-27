package salsisa.tareas.frontend.controllers;

import salsisa.tareas.frontend.servicesAPI.TareaRestCliente;

public class CreateTaskController {
    private final TareaRestCliente tareaRestCliente;

    public CreateTaskController(TareaRestCliente tareaRestCliente) {
        this.tareaRestCliente = tareaRestCliente;
    }
}

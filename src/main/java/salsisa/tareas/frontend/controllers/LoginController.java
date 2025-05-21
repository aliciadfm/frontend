package salsisa.tareas.frontend.controllers;

import salsisa.tareas.frontend.dto.GestorLoginDTO;
import salsisa.tareas.frontend.servicesAPI.GestorRestCliente;
import salsisa.tareas.frontend.views.LoginView;

public class LoginController {

    private final LoginView view;
    private GestorRestCliente gestorRestCliente;

    public LoginController(LoginView view, GestorRestCliente gestorRestCliente) {
        this.view = view;
        this.gestorRestCliente = gestorRestCliente;
    }

    public void handleLogin(String usuario, String contrasena) {
        boolean success = false;
        try {
            gestorRestCliente.login(usuario, contrasena);
            success = true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        if(success) {
            view.navigateToHome();
        } else {
            view.showErrorMessage();
        }
    }

}

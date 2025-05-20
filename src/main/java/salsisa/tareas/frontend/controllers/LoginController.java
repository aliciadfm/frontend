package salsisa.tareas.frontend.controllers;

import salsisa.tareas.frontend.views.LoginView;

public class LoginController {

    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void handleLogin(String usuario, String contrasena) {
        boolean success = "usuario".equals(usuario) && "contrasena".equals(contrasena);

        if(success) {
            view.navigateToHome();
        } else {
            //TODO
        }
    }

}

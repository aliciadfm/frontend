package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import salsisa.tareas.frontend.controllers.LoginController;
import salsisa.tareas.frontend.dto.GestorLoginDTO;
import salsisa.tareas.frontend.servicesAPI.GestorRestCliente;

@PageTitle("SH - Inicio de sesión")
@Route(value="login")
@RouteAlias(value="")
public class LoginView extends HorizontalLayout {

    private final LoginController controller;
    private GestorRestCliente gestorRestCliente;

    public LoginView(GestorRestCliente gestorRestCliente) {
        this.gestorRestCliente = gestorRestCliente;
        controller = new LoginController(this, gestorRestCliente);
        setSizeFull();
        createLogoZone();
        createLoginForm();
    }

    public void createLogoZone() {
        HorizontalLayout logoBackground = new HorizontalLayout();
        logoBackground.getStyle().set("background-color", "#b64040");
        logoBackground.setWidth("40%");
        logoBackground.setHeightFull();
        logoBackground.getStyle().set("border-radius", "0 2em 2em 0");
        add(logoBackground);
        Image image = new Image("icons/SHLogoWhite.png", "Solidarity Hub Logo");
        logoBackground.add(image);
        image.setWidth("50%");
        image.setHeight("37%");
        logoBackground.setAlignItems(Alignment.CENTER);
        logoBackground.setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public void createLoginForm() {
        VerticalLayout loginFormDiv = new VerticalLayout();
        loginFormDiv.setWidth("60%");
        add(loginFormDiv);
        LoginForm loginForm = new LoginForm();
        loginFormDiv.add(loginForm);
        loginFormDiv.setJustifyContentMode(JustifyContentMode.CENTER);
        loginFormDiv.setAlignItems(Alignment.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Iniciar sesión");
        i18n.getForm().setUsername("Usuario");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setSubmit("Iniciar sesión");
        i18n.getForm().setForgotPassword("He olvidado mi contraseña");
        loginForm.setI18n(i18n);
        loginForm.addLoginListener(event -> {
            controller.handleLogin(event.getUsername(), event.getPassword());
            loginFormDiv.setEnabled(true);
        });
        add(loginFormDiv);
    }

    public void navigateToHome() {
        UI.getCurrent().navigate("createTask");
    }
}
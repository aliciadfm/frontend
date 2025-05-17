package salsisa.tareas.frontend.views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("SH - Inicio de sesión")
@Route(value="login")
public class LoginView extends HorizontalLayout {
    public LoginView() {
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
        image.setHeight("40%");
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
        add(loginFormDiv);
    }
}

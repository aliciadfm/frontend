package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AuthService {

    private final CredencialesConfig credenciales;

    @Autowired
    public AuthService(CredencialesConfig credenciales) {
        this.credenciales = credenciales;
    }

    public HttpHeaders getAuthHeaders() {
        String auth = credenciales.getUsername() + ":" + credenciales.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}

package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.TareaDTO;

@Component
public class TareaRestCliente extends ClienteRestBase<TareaDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/tareas";

    @Autowired
    public TareaRestCliente(RestTemplate restTemplate, AuthService authService) {
        super(restTemplate, authService, BASE_URL, TareaDTO.class);
    }
}

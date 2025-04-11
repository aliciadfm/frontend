package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.AfectadoDTO;

@Component
public class AfectadoRestCliente extends ClienteRestBase<AfectadoDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/afectados";

    @Autowired
    public AfectadoRestCliente(RestTemplate restTemplate, AuthService authService) {
        super(restTemplate, authService, BASE_URL, AfectadoDTO.class);
    }
}

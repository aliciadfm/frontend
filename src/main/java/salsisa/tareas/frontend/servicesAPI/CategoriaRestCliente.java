package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.CategoriaDTO;

@Component
public class CategoriaRestCliente extends ClienteRestBase<CategoriaDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/categorias";

    @Autowired
    public CategoriaRestCliente(RestTemplate restTemplate, AuthService authService) {
        super(restTemplate, authService, BASE_URL, CategoriaDTO.class);
    }
}
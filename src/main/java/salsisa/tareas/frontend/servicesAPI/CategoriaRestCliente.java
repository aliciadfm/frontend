package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.CategoriaDTO;

@Component
public class CategoriaRestCliente extends ClienteRestBase<CategoriaDTO> {

    private static final String BASE_URL = "http://localhost:9090/api/categorias";

    @Autowired
    public CategoriaRestCliente(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL, CategoriaDTO.class);
    }
}
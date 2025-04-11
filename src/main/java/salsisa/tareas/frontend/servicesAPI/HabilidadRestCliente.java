package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.HabilidadDTO;

@Component
public class HabilidadRestCliente extends ClienteRestBase<HabilidadDTO> {

    private static final String BASE_URL = "http://localhost:9090/api/habilidades";

    @Autowired
    public HabilidadRestCliente(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL, HabilidadDTO.class);
    }
}


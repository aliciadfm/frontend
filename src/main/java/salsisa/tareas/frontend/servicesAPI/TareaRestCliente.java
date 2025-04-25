package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.TareaDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class TareaRestCliente extends ClienteRestBase<TareaDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/tareas";

    @Autowired
    public TareaRestCliente(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL, TareaDTO.class);
    }

    public List<TareaDTO> filtrarPorEstados(List<Estado> estados) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/filtro")
                .queryParam("estados", estados.toArray())
                .toUriString();

        TareaDTO[] tareasArray = getRestTemplate().getForObject(url, TareaDTO[].class);
        return Arrays.asList(tareasArray);
    }
}

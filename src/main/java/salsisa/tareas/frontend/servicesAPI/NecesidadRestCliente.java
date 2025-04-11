package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.Urgencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NecesidadRestCliente extends ClienteRestBase<NecesidadDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/necesidades";
    private static final String SIN_CUBRIR_URL = BASE_URL + "/sinCubrir";
    private static final Class<NecesidadDTO[]> NECESIDAD_ARRAY_CLASS = NecesidadDTO[].class;

    private final RestTemplate restTemplate;
    private final AuthService authService;

    @Autowired
    public NecesidadRestCliente(RestTemplate restTemplate, AuthService authService) {
        super(restTemplate, authService, BASE_URL, NecesidadDTO.class);
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    public List<NecesidadDTO> obtenerSinCubrir(FiltroNecesidadDTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SIN_CUBRIR_URL);

        if (filtro.getUrgencias() != null) {
            for (Urgencia urgencia : filtro.getUrgencias()) {
                builder.queryParam("urgencias", urgencia.name());
            }
        }

        if (filtro.getCategorias() != null) {
            for (Long categoria : filtro.getCategorias()) {
                builder.queryParam("categorias", categoria);
            }
        }

        String finalUrl = builder.toUriString();
        HttpHeaders headers = authService.getAuthHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<NecesidadDTO[]> response = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                entity,
                NECESIDAD_ARRAY_CLASS
        );

        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }
}

package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.Urgencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NecesidadRestCliente {

    private static final String BASE_URL = "https://21f594b0-23b6-4c62-8d2c-62fe254a9360.mock.pstmn.io/api/necesidades";
    private static final String SIN_CUBRIR_URL ="http:/localhost:8081/api/necesidades/sinCubrir" ;

    private final RestTemplate restTemplate;

    public NecesidadRestCliente(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<NecesidadDTO> obtenerTodos() {
        ResponseEntity<NecesidadDTO[]> response = restTemplate.getForEntity(BASE_URL, NecesidadDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public NecesidadDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, NecesidadDTO.class);
    }
    public List<NecesidadDTO> filtrarNecesidades(FiltroNecesidadDTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SIN_CUBRIR_URL); // <-- ej: "http://localhost:8081/api/necesidades/sinCubrir"

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

        ResponseEntity<NecesidadDTO[]> response = restTemplate.getForEntity(finalUrl, NecesidadDTO[].class);

        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }


//    public NecesidadDTO crear(NecesidadDTO dto) {
//        return restTemplate.postForObject(BASE_URL, dto, NecesidadDTO.class);
//    }

//    public void actualizar(Long id, NecesidadDTO dto) {
//        restTemplate.put(BASE_URL + "/" + id, dto);
//    }

//    public void eliminar(Long id) {
//        restTemplate.delete(BASE_URL + "/" + id);
//    }
}

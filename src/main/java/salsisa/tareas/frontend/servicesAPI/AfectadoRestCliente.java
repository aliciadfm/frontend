package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.AfectadoDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class AfectadoRestCliente {
    private final String BASE_URL = "https://ade24970-ffb5-4c4a-a25a-79c1f3a4d9df.mock.pstmn.io/api/afectados";

    @Autowired
    private RestTemplate restTemplate;

    public List<AfectadoDTO> obtenerTodos() {
        ResponseEntity<AfectadoDTO[]> response = restTemplate.getForEntity(BASE_URL, AfectadoDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public AfectadoDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, AfectadoDTO.class);
    }

//    public AfectadoDTO crear(AfectadoDTO habilidad) {
//        HttpEntity<AfectadoDTO> request = new HttpEntity<>(habilidad);
//        return restTemplate.postForObject(BASE_URL, request, AfectadoDTO.class);
//    }

//    public void actualizar(Long id, AfectadoDTO habilidad) {
//        HttpEntity<AfectadoDTO> request = new HttpEntity<>(habilidad);
//        restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.PUT, request, Void.class);
//    }

//    public void eliminar(Long id) {
//        restTemplate.delete(BASE_URL + "/" + id);
//    }
}

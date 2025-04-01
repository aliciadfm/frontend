package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.HabilidadDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class HabilidadesRestCliente {
    private final String BASE_URL = "";

    private final RestTemplate restTemplate;

    public HabilidadesRestCliente(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<HabilidadDTO> obtenerTodos() {
        ResponseEntity<HabilidadDTO[]> response = restTemplate.getForEntity(BASE_URL, HabilidadDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public HabilidadDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, HabilidadDTO.class);
    }

//    public HabilidadDTO crear(HabilidadDTO habilidad) {
//        HttpEntity<HabilidadDTO> request = new HttpEntity<>(habilidad);
//        return restTemplate.postForObject(BASE_URL, request, HabilidadDTO.class);
//    }

//    public void actualizar(Long id, HabilidadDTO habilidad) {
//        HttpEntity<HabilidadDTO> request = new HttpEntity<>(habilidad);
//        restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.PUT, request, Void.class);
//    }

//    public void eliminar(Long id) {
//        restTemplate.delete(BASE_URL + "/" + id);
//    }
}

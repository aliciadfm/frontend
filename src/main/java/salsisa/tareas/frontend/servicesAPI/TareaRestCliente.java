package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.TareaDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class TareaRestCliente {

    private static final String BASE_URL = "";

    @Autowired
    private RestTemplate restTemplate;

    public List<TareaDTO> obtenerTodos() {
        ResponseEntity<TareaDTO[]> response = restTemplate.getForEntity(BASE_URL, TareaDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public TareaDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, TareaDTO.class);
    }

    public TareaDTO crear(TareaDTO dto) {
        return restTemplate.postForObject(BASE_URL, dto, TareaDTO.class);
    }

    public void actualizar(Long id, TareaDTO dto) {
        restTemplate.put(BASE_URL + "/" + id, dto);
    }

    public void eliminar(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}

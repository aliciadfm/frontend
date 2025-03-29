package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.FiltroNecesidadDTO;
import salsisa.tareas.frontend.dto.NecesidadDTO;
import salsisa.tareas.frontend.dto.Urgencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NecesidadRestCliente {

    private static final String BASE_URL = "https://21f594b0-23b6-4c62-8d2c-62fe254a9360.mock.pstmn.io/api/necesidades";

    @Autowired
    private RestTemplate restTemplate;

    public List<NecesidadDTO> obtenerTodos() {
        ResponseEntity<NecesidadDTO[]> response = restTemplate.getForEntity(BASE_URL, NecesidadDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public NecesidadDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, NecesidadDTO.class);
    }

//    public List<NecesidadDTO> filtrarNecesidades(FiltroNecesidadDTO filtro) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//
//        // Añadir urgencias dinámicas
//        if (filtro.getUrgencias() != null) {
//            for (Urgencia urgencia : filtro.getUrgencias()) {
//                params.add("urgencias", urgencia.name());
//            }
//        }
//
//        // Añadir categorías dinámicas
//        if (filtro.getCategorias() != null) {
//            for (Long categoria : filtro.getCategorias()) {
//                params.add("categorias", categoria.toString());
//            }
//        }
//
//        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
//            params.add("estado", filtro.getEstado());
//        }
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        ResponseEntity<NecesidadDTO[]> response = restTemplate.postForEntity(FILTRO_URL, request, NecesidadDTO[].class);
//        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
//    }

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

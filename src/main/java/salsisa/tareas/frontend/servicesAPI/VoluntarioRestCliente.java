package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.FiltroVoluntarioDTO;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class VoluntarioRestCliente {

    private static final String BASE_URL = "https://963252b4-a3b9-4627-93ec-d9656d72237e.mock.pstmn.io/api/voluntarios";

    @Autowired
    private RestTemplate restTemplate;

    public List<VoluntarioDTO> obtenerTodos() {
        ResponseEntity<VoluntarioDTO[]> response = restTemplate.getForEntity(BASE_URL, VoluntarioDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public VoluntarioDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, VoluntarioDTO.class);
    }

//    public List<VoluntarioDTO> filtrarVoluntarios(FiltroVoluntarioDTO filtro) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
//
//        if (filtro.getFechaInicio() != null) {
//            params.add("fechaInicio", filtro.getFechaInicio().format(dateFormatter));
//        }
//        if (filtro.getFechaFin() != null) {
//            params.add("fechaFin", filtro.getFechaFin().format(dateFormatter));
//        }
//        if (filtro.getHorarioInicio() != null) {
//            params.add("horarioInicio", filtro.getHorarioInicio().format(timeFormatter));
//        }
//        if (filtro.getHorarioFin() != null) {
//            params.add("horarioFin", filtro.getHorarioFin().format(timeFormatter));
//        }
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        ResponseEntity<VoluntarioDTO[]> response = restTemplate.postForEntity(FILTRO_URL, request, VoluntarioDTO[].class);
//        return Arrays.asList(response.getBody());
//    }

//    public VoluntarioDTO crear(VoluntarioDTO dto) {
//        return restTemplate.postForObject(BASE_URL, dto, VoluntarioDTO.class);
//    }

//    public void actualizar(Long id, VoluntarioDTO dto) {
//        restTemplate.put(BASE_URL + "/" + id, dto);
//    }

//    public void eliminar(Long id) {
//        restTemplate.delete(BASE_URL + "/" + id);
//    }
}

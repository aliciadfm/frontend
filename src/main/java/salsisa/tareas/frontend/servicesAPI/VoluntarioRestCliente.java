package salsisa.tareas.frontend.servicesAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.FiltroVoluntario1DTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;
import salsisa.tareas.frontend.dto.FiltroVoluntario2DTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class VoluntarioRestCliente {

    private static final String BASE_URL = "https://963252b4-a3b9-4627-93ec-d9656d72237e.mock.pstmn.io/api/voluntarios";

    private final RestTemplate restTemplate;

    public VoluntarioRestCliente(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<VoluntarioDTO> obtenerTodos() {
        ResponseEntity<VoluntarioDTO[]> response = restTemplate.getForEntity(BASE_URL, VoluntarioDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public VoluntarioDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, VoluntarioDTO.class);
    }
    public List<VoluntarioDTO> obtenerVoluntariosValidos(FiltroVoluntario1DTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8081/api/voluntarios/validos");

        if (filtro.getFechaInicio() != null)
            builder.queryParam("fechaInicio", filtro.getFechaInicio());

        if (filtro.getFechaFin() != null)
            builder.queryParam("fechaFin", filtro.getFechaFin());

        if (filtro.getHorarioInicio() != null)
            builder.queryParam("horarioInicio", filtro.getHorarioInicio());

        if (filtro.getHorarioFin() != null)
            builder.queryParam("horarioFin", filtro.getHorarioFin());

        if (filtro.getIdCategoria() != null)
            builder.queryParam("idCategoria", filtro.getIdCategoria());

        String finalUrl = builder.toUriString();

        ResponseEntity<VoluntarioDTO[]> response = restTemplate.getForEntity(finalUrl, VoluntarioDTO[].class);
        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }
    public List<VoluntarioDTO> validarListaDeVoluntarios(FiltroVoluntario2DTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8081/api/voluntarios/validos/lista");

        if (filtro.getFechaInicio() != null)
            builder.queryParam("fechaInicio", filtro.getFechaInicio());

        if (filtro.getFechaFin() != null)
            builder.queryParam("fechaFin", filtro.getFechaFin());

        if (filtro.getHorarioInicio() != null)
            builder.queryParam("horarioInicio", filtro.getHorarioInicio());

        if (filtro.getHorarioFin() != null)
            builder.queryParam("horarioFin", filtro.getHorarioFin());

        if (filtro.getIdCategoria() != null)
            builder.queryParam("idCategoria", filtro.getIdCategoria());

        if (filtro.getVoluntarios() != null && !filtro.getVoluntarios().isEmpty()) {
            for (Long idVoluntario : filtro.getVoluntarios()) {
                builder.queryParam("voluntarios", idVoluntario);
            }
        }

        String finalUrl = builder.toUriString();

        ResponseEntity<VoluntarioDTO[]> response = restTemplate.getForEntity(finalUrl, VoluntarioDTO[].class);
        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }


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

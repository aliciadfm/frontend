package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.FiltroVoluntario1DTO;
import salsisa.tareas.frontend.dto.FiltroVoluntario2DTO;
import salsisa.tareas.frontend.dto.VoluntarioDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class VoluntarioRestCliente extends ClienteRestBase<VoluntarioDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/voluntarios";
    private static final String VOLUNTARIOS_VALIDOS_URL = BASE_URL + "/validos";
    private static final String VOLUNTARIOS_VALIDOS_LISTA_URL = BASE_URL + "/validos/lista";

    private final RestTemplate restTemplate;


    @Autowired
    public VoluntarioRestCliente(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL, VoluntarioDTO.class);
        this.restTemplate = restTemplate;

    }

    private static final Class<VoluntarioDTO[]> VOLUNTARIO_ARRAY_CLASS = VoluntarioDTO[].class;

    public List<VoluntarioDTO> obtenerVoluntariosValidos(FiltroVoluntario1DTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(VOLUNTARIOS_VALIDOS_URL);
        if (filtro.getFechaInicio() != null)
            builder.queryParam("fechaInicio", filtro.getFechaInicio());

        if (filtro.getFechaFin() != null)
            builder.queryParam("fechaFin", filtro.getFechaFin());

        if (filtro.getTurnoManana()!= null)
            builder.queryParam("turnoManana", filtro.getTurnoManana());

        if (filtro.getTurnoTarde() != null)
            builder.queryParam("turnoTarde", filtro.getTurnoTarde());

        if (filtro.getIdCategoria() != null)
            builder.queryParam("idCategoria", filtro.getIdCategoria());


        ResponseEntity<VoluntarioDTO[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                VOLUNTARIO_ARRAY_CLASS
        );

        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public List<VoluntarioDTO> validarListaDeVoluntarios(FiltroVoluntario2DTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(VOLUNTARIOS_VALIDOS_LISTA_URL);

        if (filtro.getFechaInicio() != null)
            builder.queryParam("fechaInicio", filtro.getFechaInicio());

        if (filtro.getFechaFin() != null)
            builder.queryParam("fechaFin", filtro.getFechaFin());

        if (filtro.getTurnoManana()!= null)
            builder.queryParam("turnoManana", filtro.getTurnoManana());

        if (filtro.getTurnoTarde() != null)
            builder.queryParam("turnoTarde", filtro.getTurnoTarde());

        if (filtro.getIdCategoria() != null)
            builder.queryParam("idCategoria", filtro.getIdCategoria());

        if (filtro.getVoluntarios() != null && !filtro.getVoluntarios().isEmpty()) {
            for (Long idVoluntario : filtro.getVoluntarios()) {
                builder.queryParam("voluntarios", idVoluntario);
            }
        }

        String finalUrl = builder.toUriString();


        ResponseEntity<VoluntarioDTO[]> response = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                VOLUNTARIO_ARRAY_CLASS
        );

        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

}
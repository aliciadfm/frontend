package salsisa.tareas.frontend.servicesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import salsisa.tareas.frontend.dto.Estado;
import salsisa.tareas.frontend.dto.TareaDTO;
import salsisa.tareas.frontend.dto.TareaResumenDTO;
import salsisa.tareas.frontend.dto.FiltroTareaDTO;


import java.util.Arrays;
import java.util.List;

@Component
public class TareaRestCliente extends ClienteRestBase<TareaDTO> {

    private static final String BASE_URL = "http://localhost:8081/api/tareas";

    @Autowired
    public TareaRestCliente(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL, TareaDTO.class);
    }

    public List<TareaResumenDTO> obtenerTodos(FiltroTareaDTO filtro) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL);

        if (filtro.getEstados() != null && !filtro.getEstados().isEmpty()) {
            for (String estado : filtro.getEstados()) {
                builder.queryParam("estados", estado);
            }
        }

        if (filtro.getFechaInicio() != null) {
            builder.queryParam("fechaInicio", filtro.getFechaInicio());
        }

        if (filtro.getFechaFin() != null) {
            builder.queryParam("fechaFin", filtro.getFechaFin());
        }

        if (filtro.getOrden() != null && !filtro.getOrden().isBlank()) {
            builder.queryParam("orden", filtro.getOrden());
        }

        String url = builder.toUriString();

        TareaResumenDTO[] tareasArray = getRestTemplate().getForObject(url, TareaResumenDTO[].class);
        return Arrays.asList(tareasArray);
    }

}

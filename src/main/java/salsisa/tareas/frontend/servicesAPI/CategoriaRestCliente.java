package salsisa.tareas.frontend.servicesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.CategoriaDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoriaRestCliente {

    private static final String BASE_URL = "https://68040f14-3ef3-42c3-9d80-589b9ff95cac.mock.pstmn.io/api/categorias";

    @Autowired
    private RestTemplate restTemplate;

    public List<CategoriaDTO> obtenerTodos() {
        ResponseEntity<CategoriaDTO[]> response = restTemplate.getForEntity(BASE_URL, CategoriaDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public CategoriaDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, CategoriaDTO.class);
    }

//    public CategoriaDTO crear(CategoriaDTO dto) {
//        return restTemplate.postForObject(BASE_URL, dto, CategoriaDTO.class);
//    }

//    public void actualizar(Long id, CategoriaDTO dto) {
//        restTemplate.put(BASE_URL + "/" + id, dto);
//    }

//    public void eliminar(Long id) {
//        restTemplate.delete(BASE_URL + "/" + id);
//    }
}

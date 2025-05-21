package salsisa.tareas.frontend.servicesAPI;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import salsisa.tareas.frontend.dto.GestorDTO;
import salsisa.tareas.frontend.dto.GestorLoginDTO;

@Service
public class GestorRestCliente {

        private final RestTemplate restTemplate;

        public GestorRestCliente(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public GestorDTO login(String usuario, String contrasena) {
            String url = "http://localhost:8081/api/gestores/login";

            GestorLoginDTO loginDTO = new GestorLoginDTO();
            loginDTO.setUsuario(usuario);
            loginDTO.setContrasena(contrasena);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GestorLoginDTO> request = new HttpEntity<>(loginDTO, headers);

            try {
                ResponseEntity<GestorDTO> response = restTemplate.postForEntity(url, request, GestorDTO.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    return response.getBody();
                } else {
                    throw new RuntimeException("Error al autenticar: " + response.getStatusCode());
                }

            } catch (Exception e) {
                throw new RuntimeException("Autenticación fallida: " + e.getMessage());
            }
        }
    }



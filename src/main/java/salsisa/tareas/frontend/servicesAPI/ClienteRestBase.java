package salsisa.tareas.frontend.servicesAPI;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ClienteRestBase<T> {

    private final RestTemplate restTemplate;
    private final AuthService authService;
    private final String baseUrl;
    private final Class<T> tipo;

    public ClienteRestBase(RestTemplate restTemplate, AuthService authService, String baseUrl, Class<T> tipo) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.baseUrl = baseUrl;
        this.tipo = tipo;
    }

    public List<T> obtenerTodos() {
        HttpEntity<Void> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<T[]> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                entity,
                getArrayClass()
        );

        T[] array = response.getBody();
        return array != null ? Arrays.asList(array) : new ArrayList<>();
    }


    public T obtenerPorId(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<T> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.GET,
                entity,
                tipo
        );

        return response.getBody();
    }

    public T crear(T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto, getHeaders());

        ResponseEntity<T> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                tipo
        );

        return response.getBody();
    }

    public void actualizar(Long id, T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto, getHeaders());

        restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

    public void eliminar(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getHeaders());

        restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }

    protected HttpHeaders getHeaders() {
        return authService != null ? authService.getAuthHeaders() : new HttpHeaders();
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getArrayClass() {
        return (Class<T[]>) java.lang.reflect.Array.newInstance(tipo, 0).getClass();
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}

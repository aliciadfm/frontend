package salsisa.tareas.frontend.servicesAPI;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public abstract class ClienteRestBase<T> {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final Class<T> tipo;

    public ClienteRestBase(RestTemplate restTemplate, String baseUrl, Class<T> tipo) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.tipo = tipo;
    }

    public List<T> obtenerTodos() {
        ResponseEntity<T[]> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                getArrayClass()
        );

        T[] array = response.getBody();
        return array != null ? Arrays.asList(array) : new ArrayList<>();
    }

    public T obtenerPorId(Long id) {
        ResponseEntity<T> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.GET,
                null,
                tipo
        );
        return response.getBody();
    }

    public T crear(T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto);
        ResponseEntity<T> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                tipo
        );
        return response.getBody();
    }

    public void actualizar(Long id, T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto);
        restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

    public void eliminar(Long id) {
        restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getArrayClass() {
        return (Class<T[]>) java.lang.reflect.Array.newInstance(tipo, 0).getClass();
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}

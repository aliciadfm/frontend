package salsisa.tareas.frontend.servicesAPI;

import lombok.Getter;
import org.springframework.stereotype.Component;
@Getter
@Component
public class CredencialesConfig {

    // Usuario fijo
    private final String username = "user";

    //Cambias esta linea cada vez que inicias la api
    private final String password = "270a588b-8d96-447d-9e6a-25675e057767";

}

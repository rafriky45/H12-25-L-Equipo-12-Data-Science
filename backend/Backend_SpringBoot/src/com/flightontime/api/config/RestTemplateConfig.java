package com.flightontime.api.config; // Ubicación en la carpeta de configuración

import org.springframework.context.annotation.Bean; // Permite que Spring gestione el objeto
import org.springframework.context.annotation.Configuration; // Marca la clase como configuración
import org.springframework.web.client.RestTemplate; // Cliente para peticiones HTTP

@Configuration // Indica que esta clase contiene definiciones de Beans
public class RestTemplateConfig {

    @Bean // Registra RestTemplate en el contenedor de Spring
    public RestTemplate restTemplate() {
        // Retorna una nueva instancia para realizar llamadas a APIs externas
        return new RestTemplate();
    }
}
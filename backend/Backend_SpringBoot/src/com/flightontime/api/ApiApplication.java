package com.flightontime.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {

		SpringApplication.run(ApiApplication.class, args); //Inicio de ecosistema de Spring Boot.

        System.out.println("----------------------------------------------");
        System.out.println("🚀 FlightOnTime API cargada exitosamente!");
        System.out.println("📡 Esperando peticiones en: http://localhost:8080");
        System.out.println("----------------------------------------------");
    }
}



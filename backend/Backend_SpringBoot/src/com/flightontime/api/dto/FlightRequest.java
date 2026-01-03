package com.flightontime.api.dto;// Ajusta si tu paquete es diferente

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import lombok.Data;
import jakarta.validation.constraints.NotBlank; // Para validar que no esté vacío
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data // Lombok: genera getters, setters, toString, etc.
public class FlightRequest {

    @NotBlank(message = "El nombre de la aerolínea es obligatorio.") // Validación: no vacío
    private String aerolinea;

    @NotBlank(message = "El código de origen (IATA) es necesario para la ruta.")
    private String origen;

    @NotBlank(message = "El código de destino (IATA) es necesario para la ruta.")
    private String destino;

    @NotNull(message = "La fecha y hora de partida no pueden estar vacías.")
    //@Future(message = "La fecha de partida debe ser una fecha futura.")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") //Anotación para forzar el uso del formato de fecha
    //private java.time.LocalDateTime fechaPartida; // Esta línea maneja el dato como tiempo (FECHA) de forma automática, en lugar de «String» directamente.
    private String fechaPartida; // Formato: "2025-11-10T14:30:00" » REACTIVAR ESTA LÍNEA SI SE DESEA MANEJAR COMO STRING.

    @NotNull(message = "La distancia es un dato crítico para la predicción, por favor indíque la distancia.")
    @Positive(message = "La distancia de vuelo debe ser un valor numérico positivo.")
    private Integer distanciaKm; // Entero para km
}
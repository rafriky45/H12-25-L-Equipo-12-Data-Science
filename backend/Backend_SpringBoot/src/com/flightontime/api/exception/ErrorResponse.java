package com.flightontime.api.exception; // Ubicación en excepciones

import lombok.AllArgsConstructor; // Constructor con todos los campos
import lombok.Data; // Getters, Setters y toString
import java.time.LocalDateTime; // Para marcar el tiempo del error

@Data // Genera el código repetitivo automáticamente
@AllArgsConstructor // Crea el constructor ErrorResponse(timestamp, status, ...)
public class ErrorResponse {
    private LocalDateTime timestamp; // Momento exacto del fallo
    private int status;              // Código HTTP (400, 500, 503)
    private String error;           // Nombre corto del error
    private String message;         // Explicación amigable para el usuario
}
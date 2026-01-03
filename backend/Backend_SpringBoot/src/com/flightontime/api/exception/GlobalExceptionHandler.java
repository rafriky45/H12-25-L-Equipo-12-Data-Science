package com.flightontime.api.exception;

import lombok.extern.slf4j.Slf4j; // IMPORTANTE: Para el Logger
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j // Lombok genera automáticamente la variable 'log'
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensajeDetallado = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        log.error("[VALIDACIÓN] Datos incorrectos recibidos: {}", mensajeDetallado);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "DATOS_INVALIDOS",
                "Por favor verifique sus datos: " + mensajeDetallado

        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleReadableException(HttpMessageNotReadableException ex) {
        log.error("[JSON/TIPO] Error en la lectura del cuerpo: {}", ex.getMessage());

        String mensajeUsuario = "El cuerpo de la petición tiene un error de formato (verifique comas, comillas o llaves).";
        String codigoError = "JSON_MAL_FORMADO";

        // Si el error indica que no pudo convertir un valor (ej: texto donde va un número)
        if (ex.getMessage() != null && ex.getMessage().contains("Cannot deserialize")) {
            mensajeUsuario = "Uno de los campos tiene un tipo de dato incorrecto (ej: se esperaba un número y se recibió texto).";
            codigoError = "TIPO_DATO_INCOMPATIBLE";
        }

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                codigoError,
                mensajeUsuario
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = String.format("El campo '%s' debe ser de tipo '%s'",
                ex.getName(), ex.getRequiredType().getSimpleName());

        log.error("[TIPO DE DATO] Error de conversión: {}", mensaje);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "TIPO_DATO_INCORRECTO",
                mensaje
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handlePythonDown(ResourceAccessException ex) {
        log.error("[CONEXIÓN IA] No se pudo contactar con el microservicio de Python: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "IA_OFFLINE",
                "El motor de predicción no está disponible actualmente."
        );
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("[ERROR INESPERADO] Se ha producido un fallo crítico: ", ex); // Aquí pasamos 'ex' completo para ver el StackTrace

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SERVER_ERROR",
                "Error inesperado: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
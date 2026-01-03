package com.flightontime.api.controller;

import com.flightontime.api.entity.Consulta;
import com.flightontime.api.repository.ConsultaRepository;
import com.flightontime.api.dto.FlightRequest;
import com.flightontime.api.dto.PredictionResponse;
import com.flightontime.api.service.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/flights")
public class PredictionController {

    private final PredictionService predictionService;

    // NUEVO: Repositorio para guardar las consultas
    @Autowired
    private ConsultaRepository consultaRepository;

    // ObjectMapper para convertir objetos a JSON (string)
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor para inyectar el PredictionService
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public PredictionResponse predecirVuelo(@Valid @RequestBody FlightRequest solicitud) {

        String inputJson = "";
        PredictionResponse respuesta = null;
        boolean exitoso = false;

        try {
            // Convertimos el input del usuario a JSON para guardar
            inputJson = objectMapper.writeValueAsString(solicitud);

            // Llamamos al servicio real (que conecta con FastAPI / Python)
            respuesta = predictionService.getPrediction(solicitud);

            // Si llegamos aquí, todo salió bien
            exitoso = true;

        } catch (Exception e) {
            // En caso de cualquier error, creamos una respuesta de error
            respuesta = new PredictionResponse();
            respuesta.setPrevision("Error");
            respuesta.setProbabilidad(0.0);
            // Opcional: podrías agregar un mensaje de error
            // respuesta.setMensaje("Error interno: " + e.getMessage());

            // Logueamos el error (aparecerá en consola)
            e.printStackTrace();
        } finally {
            // === GUARDADO EN BASE DE DATOS (siempre, éxito o error) ===
            try {
                String resultadoJson = objectMapper.writeValueAsString(respuesta);
                Consulta consulta = new Consulta(inputJson, resultadoJson, exitoso);
                consultaRepository.save(consulta);
            } catch (Exception saveError) {
                // Si falla el guardado, no rompemos la respuesta al usuario
                saveError.printStackTrace();
            }
        }

        return respuesta;
    }
}
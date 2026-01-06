package com.flightontime.api.controller;

import com.flightontime.api.entity.Consulta;
import com.flightontime.api.repository.ConsultaRepository;
import com.flightontime.api.dto.FlightRequest;
import com.flightontime.api.dto.PredictionResponse;
import com.flightontime.api.service.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/flights")
public class PredictionController {

    private final PredictionService predictionService;

    @Autowired
    private ConsultaRepository consultaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public PredictionResponse predecirVuelo(@Valid @RequestBody FlightRequest solicitud) {

        String inputJson = "";
        PredictionResponse respuesta = null;
        boolean exitoso = false;

        try {
            inputJson = objectMapper.writeValueAsString(solicitud);
            respuesta = predictionService.getPrediction(solicitud);
            exitoso = true;

        } catch (Exception e) {
            respuesta = new PredictionResponse();
            respuesta.setPrevision("Error");
            respuesta.setProbabilidad(0.0);
            e.printStackTrace();
        } finally {
            try {
                String resultadoJson = objectMapper.writeValueAsString(respuesta);
                Consulta consulta = new Consulta(inputJson, resultadoJson, exitoso);
                consultaRepository.save(consulta);
            } catch (Exception saveError) {
                saveError.printStackTrace();
            }
        }

        return respuesta;
    }
}
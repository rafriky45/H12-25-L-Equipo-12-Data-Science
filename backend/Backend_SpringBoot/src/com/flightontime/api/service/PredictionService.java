package com.flightontime.api.service;

import com.flightontime.api.dto.PredictionResponse;
import com.flightontime.api.dto.FlightRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    private final RestTemplate restTemplate;

    @Value("${ds.api.url}")
    private String dsApiUrl;

    public PredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Envía los datos del vuelo al microservicio de Python y retorna la predicción.
     * MODIFICACIÓN: Se cambió el orden de las clases para que coincida con el Controller.
     */
    public PredictionResponse getPrediction(FlightRequest request) { // <-- Cambiado: Recibe FlightRequest

        // --- INICIO DE BLOQUE MOCK (PARA TESTEO LOCAL) ---
        // Comenta este bloque cuando quieras usar la IA real en FastAPI
        PredictionResponse respuestaMock = new PredictionResponse();
        respuestaMock.setPrevision("On Time");
        respuestaMock.setProbabilidad(0.95);
        return respuestaMock;
        // --- FIN DE BLOQUE MOCK ---

        /*
        // LÓGICA REAL PARA FAST API (Se activará después)
        String endpoint = dsApiUrl + "/predict";

        // Enviamos "request" (FlightRequest) y esperamos 'PredictionResponse.class'
        return restTemplate.postForObject(endpoint, request, PredictionResponse.class);
        */
    }
}
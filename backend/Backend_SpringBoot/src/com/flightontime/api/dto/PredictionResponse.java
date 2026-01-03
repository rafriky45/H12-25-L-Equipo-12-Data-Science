package com.flightontime.api.dto;

import lombok.Data;

@Data
public class PredictionResponse {
    private String prevision; // "Puntual" o "Retrasado"
    private double probabilidad; // 0.78 por ejemplo
}
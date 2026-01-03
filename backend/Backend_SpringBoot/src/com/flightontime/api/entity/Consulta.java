package com.flightontime.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(columnDefinition = "TEXT")
    private String inputUsuario;

    @Column(columnDefinition = "TEXT")
    private String resultadoModelo;

    private boolean exitoso;

    // Constructores
    public Consulta() {}

    public Consulta(String inputUsuario, String resultadoModelo, boolean exitoso) {
        this.fecha = LocalDateTime.now();
        this.inputUsuario = inputUsuario;
        this.resultadoModelo = resultadoModelo;
        this.exitoso = exitoso;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getInputUsuario() { return inputUsuario; }
    public void setInputUsuario(String inputUsuario) { this.inputUsuario = inputUsuario; }

    public String getResultadoModelo() { return resultadoModelo; }
    public void setResultadoModelo(String resultadoModelo) { this.resultadoModelo = resultadoModelo; }

    public boolean isExitoso() { return exitoso; }
    public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }
}
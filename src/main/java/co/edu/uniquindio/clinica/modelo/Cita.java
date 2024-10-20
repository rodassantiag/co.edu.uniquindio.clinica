package co.edu.uniquindio.clinica.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Cita {
    private String id;
    private LocalDateTime fecha;
    private Paciente paciente;
    private Servicio servicio;
    private Factura factura;
}

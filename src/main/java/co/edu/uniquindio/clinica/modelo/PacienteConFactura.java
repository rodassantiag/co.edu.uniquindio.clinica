package co.edu.uniquindio.clinica.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PacienteConFactura {
    private Paciente paciente;
    private Factura factura;
    private Servicio servicio;
}

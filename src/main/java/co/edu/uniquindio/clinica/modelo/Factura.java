package co.edu.uniquindio.clinica.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Factura {
    private LocalDateTime fecha;
    private String id;
    private double subtotal;
    private double total;
}

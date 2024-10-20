package co.edu.uniquindio.clinica.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Servicio {
    private String id, nombre, tipo;
    private double precio;
}

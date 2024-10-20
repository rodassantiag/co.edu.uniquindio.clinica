package co.edu.uniquindio.clinica.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Servicio {
    private String id, nombre;
    private double precio;
    private boolean esGratuitoBasica;
    private boolean esGratuitoPremium;
    private boolean tieneDescuentoBasica;

}

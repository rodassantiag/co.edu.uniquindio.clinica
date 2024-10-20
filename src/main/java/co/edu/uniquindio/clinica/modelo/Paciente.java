package co.edu.uniquindio.clinica.modelo;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {
    private String nombre, cedula, telefono, email;
    private Suscripcion suscripcion;
}

package co.edu.uniquindio.clinica.factory;

import co.edu.uniquindio.clinica.controlador.ControladorPrincipal;
import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class SuscripcionPremium implements Suscripcion{


    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @Override
    public ArrayList<Servicio> getServicios() {
        return controladorPrincipal.getClinica().getServicios();
    }

    @Override
    public Factura generarFactura(Servicio servicio) {
        double precioFinal;

        if (servicio.isEsGratuitoPremium()) {
            precioFinal = 0;
        } else {
            precioFinal = servicio.getPrecio(); // Precio completo si no es gratuito
        }

        return Factura.builder()
                .fecha(LocalDateTime.now())
                .id(controladorPrincipal.generarid())
                .subtotal(servicio.getPrecio())
                .total(precioFinal)
                .build();

    }


}

package co.edu.uniquindio.clinica.factory;

import co.edu.uniquindio.clinica.controlador.ControladorPrincipal;
import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SuscripcionBasica implements Suscripcion{

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @Override
    public ArrayList<Servicio> getServicios() {
        return controladorPrincipal.getClinica().getServicios();
    }

    @Override
    public Factura generarFactura(Servicio servicio) {
        double precioFinal;

        if (servicio.isEsGratuitoBasica()) {
            precioFinal = 0;
        } else if (servicio.isTieneDescuentoBasica()) {
            precioFinal = servicio.getPrecio() * 0.8;
        } else {
            precioFinal = servicio.getPrecio();
        }

        return Factura.builder()
                .fecha(LocalDateTime.now())
                .id(controladorPrincipal.generarid())
                .subtotal(servicio.getPrecio())
                .total(precioFinal)
                .build();
    }


}

package co.edu.uniquindio.clinica.factory;

import co.edu.uniquindio.clinica.controlador.ControladorPrincipal;
import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Servicio;

import java.util.ArrayList;

public class SuscripcionBasica implements Suscripcion{

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @Override
    public ArrayList<Servicio> getServicios() {
        return controladorPrincipal.getClinica().getServiciosBasica();
    }

    @Override
    public Factura generarFactura(Servicio servicio) {
        return null;
    }
}

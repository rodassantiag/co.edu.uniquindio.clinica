package co.edu.uniquindio.clinica.factory;

import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Servicio;

import java.util.ArrayList;

public interface Suscripcion {

    ArrayList<Servicio> getServicios();

    Factura generarFactura(Servicio servicio);
}

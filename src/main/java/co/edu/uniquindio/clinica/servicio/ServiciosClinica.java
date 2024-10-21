package co.edu.uniquindio.clinica.servicio;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.modelo.Cita;
import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Paciente;
import co.edu.uniquindio.clinica.modelo.Servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ServiciosClinica {
    Suscripcion getSuscripcion(String suscripcion);

    Paciente registrarPaciente(String nombre, String cedula, String telefono, String email,
                               Suscripcion suscripcion) throws Exception;

    void eliminarPaciente(Paciente paciente);

    ArrayList<Paciente> listarPacientes();

    ArrayList<Servicio> listarServicios();

    Servicio agregarServicio(String nombre, double precio, boolean esGratuitoBasica, boolean esGratuitoPremium, boolean tieneDescuentoBasica) throws Exception;

    void eliminarServicio(Servicio servicio);

    String generarid();

    Cita agendarCita(Paciente paciente, Servicio servicio, Factura factura, LocalDateTime fecha) throws Exception;

    void EnviarFacturaSuscripcion(Paciente paciente, Factura factura, String nombreServicio, String tipoSuscripcion);
}

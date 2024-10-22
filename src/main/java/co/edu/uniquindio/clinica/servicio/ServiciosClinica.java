package co.edu.uniquindio.clinica.servicio;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.modelo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ServiciosClinica {
    Suscripcion getSuscripcion(String suscripcion);

    Paciente registrarPaciente(String nombre, String cedula, String telefono, String email,
                               Suscripcion suscripcion) throws Exception;

    void guardarPacienteConFactura(Paciente paciente, Factura factura, Servicio servicio);

    void eliminarPaciente(Paciente paciente);

    void eliminarPacienteFactura(Paciente paciente);

    ArrayList<Paciente> listarPacientes();

    ArrayList<Servicio> listarServicios();

    Servicio agregarServicio(String nombre, double precio, boolean esGratuitoBasica, boolean esGratuitoPremium, boolean tieneDescuentoBasica) throws Exception;

    void eliminarServicio(Servicio servicio);

    String generarid();

    Cita agendarCita(Paciente paciente, Servicio servicio, Factura factura, LocalDateTime fecha) throws Exception;

    PacienteConFactura obtenerPacienteConFactura(String cedula) throws Exception;

    void enviarFacturaSuscripcion(Paciente paciente, Factura factura, String nombreServicio, String tipoSuscripcion);

    void enviarFacturaCita(Cita cita);

    void cancelarCita(Cita cita)throws Exception;

    void correoCancelacion(Cita cita);
}

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

    ArrayList<Paciente> listarPacientes();

    Servicio agregarServicio(String id, String nombre, double precio, String tipo) throws Exception;

    String generarid();

    Cita agendarCita(Paciente paciente, Servicio servicio, Factura factura, LocalDateTime fecha) throws Exception;
}

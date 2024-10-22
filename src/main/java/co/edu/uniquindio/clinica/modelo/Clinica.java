package co.edu.uniquindio.clinica.modelo;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.factory.SuscripcionBasica;
import co.edu.uniquindio.clinica.factory.SuscripcionPremium;
import co.edu.uniquindio.clinica.servicio.ServiciosClinica;
import co.edu.uniquindio.clinica.utils.EnvioEmail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;


@Setter
@Getter
public class Clinica implements ServiciosClinica {

    private ArrayList<Paciente> pacientes;
    private ArrayList<Servicio> servicios;
    private ArrayList<Cita> citas;
    private ArrayList<PacienteConFactura> pacienteConFacturas;

    public Clinica() {
        this.pacientes = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.citas = new ArrayList<>();
        this.pacienteConFacturas = new ArrayList<>();

    }

    @Override
    public Suscripcion getSuscripcion(String suscripcion) {

        if (suscripcion.equals("Premium")) {
            return new SuscripcionPremium();
        }

        if (suscripcion.equals("Básica")) {
            return new SuscripcionBasica();

        } else {
            return null;
        }
    }

    @Override
    public Paciente registrarPaciente(String nombre, String cedula, String telefono, String email,
                                      Suscripcion suscripcion) throws Exception {

        if (nombre.isBlank()) {
            throw new Exception("El nombre es Obligatorio");
        }

        if (cedula.isBlank()) {
            throw new Exception("La cedula es Obligatoria");
        }

        if (!cedula.matches("\\d+")) {
            throw new Exception("La cédula no es válida");
        }

        if (obtenerPaciente(cedula) != null){
            throw new Exception("Ya existe un paciente registrado con la misma cédula");
        }

        if (obtenerPacienteCorreo(email) != null){
            throw new Exception("Ya existe un paciente registrado con el mismo correo");
        }

        if (obtenerPacienteTelefono(telefono) != null){
            throw new Exception("Ya existe un paciente registrado con el mismo teléfono");
        }

        if (telefono.isBlank()) {
            throw new Exception("El teléfono es Obligatorio");
        }

        if (!telefono.matches("\\d+")) {
            throw new Exception("El número de teléfono es inválido");
        }

        if (email.isBlank()) {
            throw new Exception("El email es Obligatorio");
        }

        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailRegex)) {
            throw new Exception("El formato del email es inválido");
        }

        Paciente paciente = Paciente.builder()
                .nombre(nombre)
                .cedula(cedula)
                .telefono(telefono)
                .email(email)
                .suscripcion(suscripcion)
                .build();

        pacientes.add(paciente);
        return paciente;
    }

    public Paciente obtenerPaciente(String cedula){
        for (Paciente paciente : pacientes){
            if (paciente.getCedula().equals(cedula)){
                return paciente;
            }
        }
        return null;
    }

    public Paciente obtenerPacienteCorreo(String correo){
        for (Paciente paciente : pacientes){
            if (paciente.getEmail().equals(correo)){
                return paciente;
            }
        }
        return null;
    }

    public Paciente obtenerPacienteTelefono(String telefono){
        for (Paciente paciente : pacientes){
            if (paciente.getTelefono().equals(telefono)){
                return paciente;
            }
        }
        return null;
    }

    @Override
    public void guardarPacienteConFactura(Paciente paciente, Factura factura, Servicio servicio) {
        pacienteConFacturas.add(PacienteConFactura.builder()
                .paciente(paciente)
                .factura(factura)
                .servicio(servicio)
                .build());
    }

    @Override
    public void eliminarPaciente(Paciente paciente){
        pacientes.remove(paciente);
    }

    @Override
    public void eliminarPacienteFactura(Paciente paciente){
        pacienteConFacturas.removeIf(pacienteConFactura -> pacienteConFactura.getPaciente().equals(paciente));
    }

    @Override
    public ArrayList<Paciente> listarPacientes(){
        return pacientes;
    }

    @Override
    public ArrayList<Servicio> listarServicios(){
        return servicios;
    }

    @Override
    public Servicio agregarServicio(String nombre, double precio, boolean esGratuitoBasica, boolean esGratuitoPremium, boolean tieneDescuentoBasica) throws Exception{
        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if (precio < 0){
            throw new Exception("El precio no puede ser negativo");
        }

        Servicio servicio = Servicio.builder()
                .id(generarid())
                .nombre(nombre)
                .precio(precio)
                .esGratuitoPremium(esGratuitoPremium)
                .esGratuitoBasica(esGratuitoBasica)
                .tieneDescuentoBasica(tieneDescuentoBasica)
                .build();

        servicios.add(servicio);

        return servicio;
    }

    @Override
    public void eliminarServicio(Servicio servicio){
        servicios.remove(servicio);
    }

    @Override
    public String generarid() {
        StringBuilder codigoRegistro = new StringBuilder();


        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            codigoRegistro.append(numero);
        }

        return codigoRegistro.toString();
    }

    @Override
    public Cita agendarCita(Paciente paciente, Servicio servicio, Factura factura, LocalDateTime fecha) throws Exception {

        if (fecha.isBefore(LocalDateTime.now())){
            throw new Exception("La fecha no puede ser menor a la fecha actual");
        }

        for (Cita citaExistente : citas) {
            if (citaExistente.getFecha().equals(fecha)) {
                throw new Exception("Ya existe una cita programada para esta fecha y hora.");
            }
        }

        Cita cita = Cita.builder()
                .id(generarid())
                .paciente(paciente)
                .servicio(servicio)
                .factura(factura)
                .fecha(fecha)
                .build();
        citas.add(cita);
        return cita;
    }




    @Override
    public PacienteConFactura obtenerPacienteConFactura(String cedula) throws Exception {
        for (PacienteConFactura pacienteConFactura : pacienteConFacturas){
            if (pacienteConFactura.getPaciente().getCedula().equals(cedula)){
                return pacienteConFactura;
            }
        }

        throw new Exception("No se ha encontrado ningún paciente con la cédula proporcionada");
    }

    @Override
    public void enviarFacturaSuscripcion(Paciente paciente, Factura factura, String nombreServicio, String tipoSuscripcion) {
        String correo = paciente.getEmail();
        String nombrePaciente = paciente.getNombre();
        LocalDateTime fecha = factura.getFecha();
        String id = factura.getId();
        double subTotal = factura.getSubtotal();
        double total = factura.getTotal();

        String asunto = "Factura de Suscripción - Clínica";

        String mensaje = "Hola " + nombrePaciente + ",\n\n" +
                "Gracias por usar nuestros servicios. Aquí están los detalles de su factura:\n\n" +
                "Fecha de la factura: " + fecha + "\n" +
                "ID de la factura: " + id + "\n" +
                "Suscripción: " + tipoSuscripcion + "\n" +
                "Servicio: " + nombreServicio + "\n" +
                "Subtotal: $" + String.format("%.2f", subTotal) + "\n" +
                "Total: $" + String.format("%.2f", total) + "\n\n" +
                "Si tiene alguna pregunta, no dude en contactarnos.\n" +
                "Atentamente,\n" +
                "Clínica";

        EnvioEmail envioEmail = new EnvioEmail(correo, asunto, mensaje);
        envioEmail.enviarNotificacion();
    }

    @Override
    public void enviarFacturaCita(Cita cita) {
        String correo = cita.getPaciente().getEmail();
        String nombrePaciente = cita.getPaciente().getNombre();
        LocalDateTime fecha = cita.getFecha();
        String id = cita.getId();
        String nombreServicio = cita.getServicio().getNombre();
        double subTotal = cita.getFactura().getSubtotal();
        double total = cita.getFactura().getTotal();

        String asunto = "Factura de Suscripción - Clínica";

        String mensaje = "Hola " + nombrePaciente + ",\n\n" +
                "Gracias por usar nuestros servicios. Aquí están los detalles de su cita:\n\n" +
                "Fecha de la cita: " + fecha + "\n" +
                "ID de la cita: " + id + "\n" +
                "Servicio: " + nombreServicio + "\n" +
                "Subtotal: $" + String.format("%.2f", subTotal) + "\n" +
                "Total: $" + String.format("%.2f", total) + "\n\n" +
                "Si tiene alguna pregunta, no dude en contactarnos.\n" +
                "Atentamente,\n" +
                "Clínica";

        EnvioEmail envioEmail = new EnvioEmail(correo, asunto, mensaje);
        envioEmail.enviarNotificacion();
    }

    @Override
    public void cancelarCita(Cita cita){
        citas.remove(cita);
    }

    @Override
    public void correoCancelacion(Cita cita){
        String correo = cita.getPaciente().getEmail();
        String nombrePaciente = cita.getPaciente().getNombre();
        LocalDateTime fecha = cita.getFecha();
        String id = cita.getId();
        String nombreServicio = cita.getServicio().getNombre();
        double subTotal = cita.getFactura().getSubtotal();
        double total = cita.getFactura().getTotal();

        String asunto = "Cancelación de Cita - Clínica";

        String mensaje = "Hola " + nombrePaciente + ",\n\n" +
                "Se le informa que su cita programada para la fecha:" +fecha +" ha sido cancelada.\n\n" +
                "ID de la cita: " + id + "\n" +
                "Servicio: " + nombreServicio + "\n" +
                "Subtotal: $" + String.format("%.2f", subTotal) + "\n" +
                "Total: $" + String.format("%.2f", total) + "\n\n" +
                "Para más información, no dude en contactarnos.\n" +
                "Atentamente,\n" +
                "Clínica";

        EnvioEmail envioEmail = new EnvioEmail(correo, asunto, mensaje);
        envioEmail.enviarNotificacion();

    }


    public LocalDateTime generarFechaHoraRandom(LocalDate fecha) {
        int horaMinima = 6;
        int horaMaxima = 20;

        Random random = new Random();


        int horaAleatoria = random.nextInt(horaMaxima - horaMinima + 1) + horaMinima;

        int minutoAleatorio = random.nextInt(60);


        return fecha.atTime(horaAleatoria, minutoAleatorio);
    }



}



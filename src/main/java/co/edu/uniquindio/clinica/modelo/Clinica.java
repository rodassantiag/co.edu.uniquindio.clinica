package co.edu.uniquindio.clinica.modelo;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.factory.SuscripcionBasica;
import co.edu.uniquindio.clinica.factory.SuscripcionPremium;
import co.edu.uniquindio.clinica.servicio.ServiciosClinica;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Setter
@Getter
public class Clinica implements ServiciosClinica {

    private ArrayList<Paciente> pacientes;
    private ArrayList<Servicio> servicios;
    private ArrayList<Cita> citas;

    public Clinica(){
        this.pacientes = new ArrayList<>();
        this.servicios= new ArrayList<>();
        this.citas = new ArrayList<>();

    }

    @Override
    public Suscripcion getSuscripcion(String suscripcion){

        if (suscripcion.equals("Premium")) {
            return new SuscripcionPremium();
        }

        if (suscripcion.equals("Básica")){
            return new SuscripcionBasica();

        }else {
            return null;
        }
    }

    @Override
    public Paciente registrarPaciente(String nombre, String cedula, String telefono, String email,
                                      Suscripcion suscripcion) throws Exception{

        if (nombre.isBlank()){
            throw new Exception("El nombre es Obligatorio");
        }

        if (cedula.isBlank()){
            throw new Exception("La cedula es Obligatoria");
        }

        if (telefono.isBlank()){
            throw new Exception("El telefono es Obligatorio");
        }

        if (email.isBlank()){
            throw new Exception("El email es Obligatorio");
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
        for (Cita citaExistente : citas) {
            if (citaExistente.getFecha().equals(fecha)) {
                throw new Exception("Ya existe una cita programada para esta fecha y hora.");
            }
        }

        Cita cita = Cita.builder()
                .paciente(paciente)
                .servicio(servicio)
                .factura(factura)
                .fecha(fecha)
                .build();
        citas.add(cita);
        return cita;
    }





}

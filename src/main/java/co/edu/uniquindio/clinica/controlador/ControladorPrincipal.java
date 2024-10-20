package co.edu.uniquindio.clinica.controlador;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.modelo.*;
import co.edu.uniquindio.clinica.servicio.ServiciosClinica;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class ControladorPrincipal implements ServiciosClinica {

    private final Clinica clinica;

    public static ControladorPrincipal INSTANCIA;

    public ControladorPrincipal(){clinica = new Clinica();}

    public static ControladorPrincipal getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws Exception {

        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.setTitle(tituloVentana);

        // Mostrar la nueva ventana
        stage.show();

        return loader;
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    @Override
    public Suscripcion getSuscripcion(String suscripcion) {
        return clinica.getSuscripcion(suscripcion);
    }

    @Override
    public Paciente registrarPaciente(String nombre, String cedula, String telefono, String email, Suscripcion suscripcion) throws Exception {
        return clinica.registrarPaciente(nombre, cedula, telefono, email, suscripcion);
    }

    @Override
    public ArrayList<Paciente> listarPacientes() {
        return clinica.listarPacientes();
    }

    @Override
    public ArrayList<Servicio> listarServicios() {
        return clinica.listarServicios();
    }

    @Override
    public Servicio agregarServicio(String nombre, double precio, boolean esGratuitoBasica, boolean esGratuitoPremium, boolean tieneDescuentoBasica) throws Exception {
        return clinica.agregarServicio(nombre, precio, esGratuitoBasica, esGratuitoPremium, tieneDescuentoBasica);
    }


    @Override
    public String generarid() {
        return clinica.generarid();
    }

    @Override
    public Cita agendarCita(Paciente paciente, Servicio servicio, Factura factura, LocalDateTime fecha) throws Exception {
        return clinica.agendarCita(paciente, servicio, factura, fecha);
    }
}

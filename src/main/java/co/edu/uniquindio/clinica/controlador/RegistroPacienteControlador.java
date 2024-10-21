package co.edu.uniquindio.clinica.controlador;

import co.edu.uniquindio.clinica.factory.Suscripcion;
import co.edu.uniquindio.clinica.modelo.Factura;
import co.edu.uniquindio.clinica.modelo.Paciente;
import co.edu.uniquindio.clinica.modelo.Servicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroPacienteControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TextField txtNombre, txtCedula, txtTelefono, txtEmail;

    @FXML
    private ComboBox<String> txtSuscripcion;
    @FXML
    private TableColumn<Servicio, String> colNombre;
    @FXML
    private TableColumn<Servicio, String> colPrecio;
    @FXML
    private TableView<Servicio> servicioTableView;


    public void agregarPaciente()throws Exception{

        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String suscripcionTexto = txtSuscripcion.getValue();

            if (suscripcionTexto == null){
                controladorPrincipal.crearAlerta("Escoja un tipo de suscripción", Alert.AlertType.ERROR);
                return;
            }

            if (servicioTableView == null) {
                controladorPrincipal.crearAlerta("Aún no hay servicios creados", Alert.AlertType.ERROR);
            }

            Suscripcion suscripcion = controladorPrincipal.getSuscripcion(suscripcionTexto);

            Servicio servicioSeleccionado = servicioTableView.getSelectionModel().getSelectedItem();
            if (servicioSeleccionado != null){
                Factura factura = suscripcion.generarFactura(servicioSeleccionado);
                Paciente paciente = controladorPrincipal.registrarPaciente(nombre, cedula, telefono, email, suscripcion);
                controladorPrincipal.guardarPacienteConFactura(paciente, factura, servicioSeleccionado);
                controladorPrincipal.crearAlerta("El Paciente ha sido registrado exitosamente," +
                        " se ha enviado la factura al correo del mismo", Alert.AlertType.INFORMATION);
                limpiarCampos();
                controladorPrincipal.enviarFacturaSuscripcion(paciente,factura, servicioSeleccionado.getNombre(), suscripcionTexto);

            } else {
                controladorPrincipal.crearAlerta("Elija un servicio", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void limpiarCampos(){
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSuscripcion.setItems(FXCollections.observableArrayList("Premium", "Básica"));
        txtSuscripcion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarServicios(newValue);
            }
        });
    }

    private void cargarServicios(String suscripcionTexto) {
        try {
            Suscripcion suscripcion = controladorPrincipal.getSuscripcion(suscripcionTexto);

            if (suscripcion != null) {


                var listaServicios = FXCollections.observableArrayList(suscripcion.getServicios());


                if (listaServicios.isEmpty()) {
                    controladorPrincipal.crearAlerta("No hay servicios creados para esta suscripción. Por favor, cree los servicios para continuar.", Alert.AlertType.WARNING);
                } else {

                    servicioTableView.setItems(listaServicios);
                    colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                    colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
                }
            } else {
                controladorPrincipal.crearAlerta("La suscripción seleccionada no tiene servicios asociados.", Alert.AlertType.WARNING);
                servicioTableView.setItems(null);  // Limpiar la tabla si no hay servicios
            }
        } catch (Exception e) {
            e.printStackTrace();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void irInicio() throws Exception{
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(servicioTableView);
    }


}

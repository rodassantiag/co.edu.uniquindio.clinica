package co.edu.uniquindio.clinica.controlador;

import co.edu.uniquindio.clinica.factory.Suscripcion;
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

            Suscripcion suscripcion = controladorPrincipal.getSuscripcion(suscripcionTexto);

            Servicio servicioSeleccionado = servicioTableView.getSelectionModel().getSelectedItem();
            if (servicioSeleccionado != null){
                suscripcion.generarFactura(servicioSeleccionado);
                controladorPrincipal.registrarPaciente(nombre, cedula, telefono, email, suscripcion);
                controladorPrincipal.crearAlerta("El Paciente ha sido registrado exitosamente", Alert.AlertType.INFORMATION);
            } else {
                controladorPrincipal.crearAlerta("Elija un servicio", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
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
                servicioTableView.setItems(FXCollections.observableArrayList(suscripcion.getServicios()));

                colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
            } else {
                controladorPrincipal.crearAlerta("La suscripción seleccionada no tiene servicios asociados.", Alert.AlertType.WARNING);
                servicioTableView.setItems(FXCollections.emptyObservableList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}

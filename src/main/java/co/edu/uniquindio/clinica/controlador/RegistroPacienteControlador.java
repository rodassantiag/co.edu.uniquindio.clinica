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

    private TextField txtNombre, txtCedula, txtTelefono, txtEmail;

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
            controladorPrincipal.registrarPaciente(nombre, cedula, telefono, email, suscripcion);
            controladorPrincipal.crearAlerta("El Paciente ha sido registrado exitosamente", Alert.AlertType.INFORMATION);


            servicioTableView.setItems(FXCollections.observableArrayList(suscripcion.getServicios()));

            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getNombre())));
            colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getPrecio())));

            servicioTableView.setVisible(true);

        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicioTableView.setVisible(false);
    }
}

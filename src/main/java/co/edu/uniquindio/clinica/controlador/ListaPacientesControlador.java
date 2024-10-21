package co.edu.uniquindio.clinica.controlador;

import co.edu.uniquindio.clinica.modelo.Paciente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ListaPacientesControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private TableColumn<Paciente, String> colNombre;
    @FXML
    private TableColumn<Paciente, String> colCedula;
    @FXML
    private TableColumn<Paciente, String> colTelefono;
    @FXML
    private TableColumn<Paciente, String> colCorreo;
    @FXML
    private TableView<Paciente> tablaPacientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPacientes();
    }

    public void cargarPacientes(){

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tablaPacientes.setItems(FXCollections.observableArrayList(controladorPrincipal.listarPacientes()));
    }

    public void irInicio() throws Exception{
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(tablaPacientes);
    }

    public void eliminarPaciente(){
        try {
            Paciente paciente = tablaPacientes.getSelectionModel().getSelectedItem();
            if (paciente != null){
                controladorPrincipal.eliminarPaciente(paciente);
                tablaPacientes.setItems(FXCollections.observableArrayList(controladorPrincipal.listarPacientes()));
                controladorPrincipal.crearAlerta("El paciente ha sido eliminado exitosamente", Alert.AlertType.INFORMATION);

            }else {
                controladorPrincipal.crearAlerta("Elija un paciente", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}

package co.edu.uniquindio.clinica.controlador;

import co.edu.uniquindio.clinica.modelo.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class CitaControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private TextField txtCedula;
    @FXML
    private DatePicker txtFechaCita;
    @FXML
    private TableColumn<Cita, String> colId, colPaciente, colCedula, colServicio, colIdFactura, colValor, colFecha;
    @FXML
    private TableView<Cita> tablaCitas;

    public void agendarCita() throws Exception{
        try {
            String cedula = txtCedula.getText();

            if (cedula == null){
                controladorPrincipal.crearAlerta("Ingrese la cédula del paciente ", Alert.AlertType.ERROR);
            }

            LocalDate fecha = txtFechaCita.getValue();

            if (fecha == null){
                controladorPrincipal.crearAlerta("Ingrese una fecha", Alert.AlertType.ERROR);
            }

            assert fecha != null;
            LocalDateTime fechaHora = controladorPrincipal.getClinica().generarFechaHoraRandom(fecha);

            PacienteConFactura pacienteConFactura = controladorPrincipal.obtenerPacienteConFactura(cedula);

            if (pacienteConFactura != null){
                Paciente paciente = pacienteConFactura.getPaciente();
                Servicio servicio = pacienteConFactura.getServicio();
                Factura factura = pacienteConFactura.getFactura();

                Cita cita = controladorPrincipal.agendarCita(paciente, servicio, factura, fechaHora);
                tablaCitas.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getCitas()));
                controladorPrincipal.crearAlerta("Se ha agendado la cita existosamente, se ha enviado un correo " +
                        " con la imformación de la cita al paciente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                controladorPrincipal.enviarFacturaCita(cita);
            }

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void limpiarCampos(){
        txtCedula.clear();
        txtFechaCita.setValue(null);
    }

    public void eliminarCita()throws Exception{
        try {
            Cita cita = tablaCitas.getSelectionModel().getSelectedItem();

            if (cita != null){
                controladorPrincipal.cancelarCita(cita);
                tablaCitas.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getCitas()));
                controladorPrincipal.crearAlerta("La cita ha sido cancelada exitosamente, se ha enviado un correo informandole al paciente", Alert.AlertType.INFORMATION);
                controladorPrincipal.correoCancelacion(cita);

            }else {
                controladorPrincipal.crearAlerta("Seleccione una cita", Alert.AlertType.WARNING);
            }
        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void irInicio() throws Exception{
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(txtFechaCita);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarCitas();

    }

    public void cargarCitas(){
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colPaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getNombre()));
        colCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getCedula()));
        colServicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServicio().getNombre()));
        colIdFactura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFactura().getId()));
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFactura().getTotal())));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));
        tablaCitas.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getCitas()));
    }
}

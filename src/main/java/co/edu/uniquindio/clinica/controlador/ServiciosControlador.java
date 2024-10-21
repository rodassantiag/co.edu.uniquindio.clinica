package co.edu.uniquindio.clinica.controlador;


import co.edu.uniquindio.clinica.modelo.Servicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServiciosControlador implements Initializable {

    @FXML
    private TextField txtNombre, txtPrecio;

    @FXML
    private CheckBox chkGratuitoBasica, chkGratuitoPremium, chkDescuentoBasica;

    @FXML
    private TableColumn<Servicio, String> colId, colNombre, colPrecio, colGratuitoBasica, colDescuentoBasica,
            colGratuitoPremium;
    @FXML
    private TableView<Servicio> tablaServicios;



    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void agregarServicio() throws Exception{
        try {
            String nombre = txtNombre.getText();
            String precioTexto = txtPrecio.getText();
            boolean esGratuitoBasica = chkGratuitoBasica.isSelected();
            boolean esGratuitoPremium = chkGratuitoPremium.isSelected();
            boolean tieneDescuentoBasica = chkDescuentoBasica.isSelected();

            if (!precioTexto.matches("\\d+")) {
                throw new Exception("El número de teléfono es inválido");
            }

            double precio = Double.parseDouble(txtPrecio.getText());

            controladorPrincipal.agregarServicio(nombre, precio, esGratuitoBasica, esGratuitoPremium, tieneDescuentoBasica);
            tablaServicios.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getServicios()));


            controladorPrincipal.crearAlerta("El servicio se ha agregado Correctamente", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void eliminarServicio(){
        try {
            Servicio servicioSeleccionado = tablaServicios.getSelectionModel().getSelectedItem();

            if (servicioSeleccionado != null){
                controladorPrincipal.eliminarServicio(servicioSeleccionado);
                tablaServicios.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getServicios()));
                controladorPrincipal.crearAlerta("El servicio se ha eliminado exitosamente", Alert.AlertType.INFORMATION);
            }else {
                controladorPrincipal.crearAlerta("Elija un servicio", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarServicios();

    }

    private void cargarServicios() {

        tablaServicios.setItems(FXCollections.observableArrayList(controladorPrincipal.getClinica().getServicios()));

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        colGratuitoBasica.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEsGratuitoBasica() ? "Sí" : "No"));
        colDescuentoBasica.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isTieneDescuentoBasica() ? "Sí" : "No"));
        colGratuitoPremium.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEsGratuitoPremium() ? "Sí" : "No"));
    }

    public void irInicio() throws Exception{
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(tablaServicios);
    }
}

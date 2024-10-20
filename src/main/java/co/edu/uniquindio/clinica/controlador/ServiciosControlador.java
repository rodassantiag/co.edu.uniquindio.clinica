package co.edu.uniquindio.clinica.controlador;


import co.edu.uniquindio.clinica.modelo.Servicio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiciosControlador implements Initializable {

    @FXML
    private TextField txtNombre, txtPrecio;

    @FXML
    private CheckBox chkGratuitoBasica, chkGratuitoPremium, chkDescuentoBasica;


    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void agregarServicio() throws Exception{
        try {
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            boolean esGratuitoBasica = chkGratuitoBasica.isSelected();
            boolean esGratuitoPremium = chkGratuitoPremium.isSelected();
            boolean tieneDescuentoBasica = chkDescuentoBasica.isSelected();


            controladorPrincipal.agregarServicio(nombre, precio, esGratuitoBasica, esGratuitoPremium, tieneDescuentoBasica);

            controladorPrincipal.crearAlerta("El servicio se ha agregado Correctamente", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

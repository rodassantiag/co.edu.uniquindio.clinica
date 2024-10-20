package co.edu.uniquindio.clinica.controlador;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void irRegistroPaciente() throws Exception{
        controladorPrincipal.navegarVentana("/registroPaciente.fxml", "registro paciente");
    }
}

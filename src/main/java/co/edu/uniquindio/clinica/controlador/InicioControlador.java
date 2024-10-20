package co.edu.uniquindio.clinica.controlador;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void irRegistroPaciente() throws Exception{
        controladorPrincipal.navegarVentana("/registroPaciente.fxml", "Registro Paciente");
    }

    public void irAgregarServicio() throws Exception{
        controladorPrincipal.navegarVentana("/creacionServicio.fxml", "creacion Servicio");
    }
}

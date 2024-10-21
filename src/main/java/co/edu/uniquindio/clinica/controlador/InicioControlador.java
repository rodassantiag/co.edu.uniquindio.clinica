package co.edu.uniquindio.clinica.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private Button boton;

    public void irRegistroPaciente() throws Exception{
        controladorPrincipal.navegarVentana("/registroPaciente.fxml", "Registro Paciente");
        controladorPrincipal.cerrarVentana(boton);
    }

    public void irAgregarServicio() throws Exception{
        controladorPrincipal.navegarVentana("/creacionServicio.fxml", "creacion Servicio");
        controladorPrincipal.cerrarVentana(boton);
    }

    public void irListaPacientes() throws Exception{
        controladorPrincipal.navegarVentana("/pacientes.fxml", "Lista Pacientes");
        controladorPrincipal.cerrarVentana(boton);
    }

    public void irAgendarCita() throws Exception{
        controladorPrincipal.navegarVentana("/agendarCita.fxml", "Lista Pacientes");
        controladorPrincipal.cerrarVentana(boton);
    }
}

package transcesar.service;

import transcesar.dao.ConductorDAO;
import transcesar.dao.PasajeroDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import java.util.List;

public class PersonaService {

    private ConductorDAO conductorDAO = new ConductorDAO();
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public boolean registrarConductor(Conductor c) {
        if (c.getLicencia() == null || c.getLicencia().isEmpty()) {
            System.out.println("Error: el conductor no tiene licencia registrada.");
            return false;
        }
        conductorDAO.guardar(c);
        System.out.println("Conductor registrado correctamente.");
        return true;
    }

    public boolean registrarPasajero(Pasajero p) {
        pasajeroDAO.guardar(p);
        System.out.println("Pasajero registrado correctamente.");
        return true;
    }

    public List<Conductor> listarConductores() {
        return conductorDAO.listar();
    }

    public List<Pasajero> listarPasajeros() {
        return pasajeroDAO.listar();
    }
}
package transcesar.service;

import transcesar.dao.ConductorDAO;
import transcesar.dao.PasajeroDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import java.time.LocalDate;
import java.util.List;

public class PersonaService {

    private ConductorDAO conductorDAO = new ConductorDAO();
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public boolean fechaNacimientoValida(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        if (fechaNacimiento.isAfter(hoy)) {
            System.out.println("Error: la fecha de nacimiento no puede ser futura.");
            return false;
        }
        if (fechaNacimiento.isBefore(LocalDate.of(1900, 1, 1))) {
            System.out.println("Error: fecha de nacimiento inválida (muy antigua).");
            return false;
        }
        return true;
    }

    public boolean registrarConductor(Conductor c) {
        if (!fechaNacimientoValida(c.getFechaNacimiento())) return false;
        if (c.getLicencia() == null || c.getLicencia().isEmpty()) {
            System.out.println("Error: el conductor no tiene licencia registrada.");
            return false;
        }
        conductorDAO.guardar(c);
        System.out.println("Conductor registrado correctamente.");
        return true;
    }

    public boolean registrarPasajero(Pasajero p) {
        if (!fechaNacimientoValida(p.getFechaNacimiento())) return false;
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

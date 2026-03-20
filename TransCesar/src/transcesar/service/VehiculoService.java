package transcesar.service;

import transcesar.dao.VehiculoDAO;
import transcesar.model.Vehiculo;
import transcesar.model.Conductor;
import java.util.List;

public class VehiculoService {

    private VehiculoDAO dao = new VehiculoDAO();

    public boolean registrarVehiculo(Vehiculo v) {
        if (placaExiste(v.getPlaca())) {
            System.out.println("Error: ya existe un vehículo con la placa " + v.getPlaca());
            return false;
        }
        dao.guardar(v);
        System.out.println("Vehículo registrado correctamente.");
        return true;
    }

    public boolean asignarConductor(Vehiculo v, Conductor c) {
        if (c.getLicencia() == null || c.getLicencia().isEmpty()) {
            System.out.println("Error: el conductor no tiene licencia registrada.");
            return false;
        }
        System.out.println("Conductor " + c.getNombre() + " asignado al vehículo " + v.getPlaca());
        return true;
    }

    public boolean placaExiste(String placa) {
        for (Vehiculo v : dao.obtenerTodos()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }
        return false;
    }

    public List<Vehiculo> consultarVehiculos() {
        return dao.obtenerTodos();
    }
}
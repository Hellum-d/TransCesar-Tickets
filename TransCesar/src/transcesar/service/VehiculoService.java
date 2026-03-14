package transcesar.service;

import transcesar.dao.VehiculoDAO;
import transcesar.model.Vehiculo;
import java.util.List;


public class VehiculoService {
    private VehiculoDAO dao = new VehiculoDAO();

    // Método para que el sistema registre vehículos formalmente
    public void registrarVehiculo(Vehiculo v) {
        // Aquí se pueden agregar validaciones en el futuro
        if (v != null && v.getPlaca() != null) {
            dao.guardar(v);
            System.out.println("LOG: Vehículo con placa " + v.getPlaca() + " registrado en el DAO.");
        }
    }

    // Método para obtener la lista desde el DAO
    public List<Vehiculo> consultarVehiculos() {
        return dao.obtenerTodos();
    }
}
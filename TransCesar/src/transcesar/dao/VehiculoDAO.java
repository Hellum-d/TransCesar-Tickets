package transcesar.dao;

import transcesar.model.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private List<Vehiculo> listaVehiculos = new ArrayList<>();

    // Método para guardar un vehículo
    public void guardar(Vehiculo v) {
        listaVehiculos.add(v);
    } 

    // Método para listar todos los vehículos
    public List<Vehiculo> obtenerTodos() {
        return listaVehiculos;
    }
}
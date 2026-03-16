package transcesar.dao;

import transcesar.model.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private List<Vehiculo> listaVehiculos = new ArrayList<>();

    public void guardar(Vehiculo v) {
        listaVehiculos.add(v);
    } 

    public List<Vehiculo> obtenerTodos() {
        return listaVehiculos;
    }
}
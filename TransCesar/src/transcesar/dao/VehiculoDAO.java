package transcesar.dao;

import transcesar.model.Vehiculo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private static final String ARCHIVO = "vehiculos.txt";
    private List<Vehiculo> listaVehiculos = new ArrayList<>();

    public VehiculoDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Vehiculo v) {
        listaVehiculos.add(v);
        guardarEnArchivo(v);
    }

    public List<Vehiculo> obtenerTodos() {
        return listaVehiculos;
    }

    private void guardarEnArchivo(Vehiculo v) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(v.getPlaca() + ";" + v.getClass().getSimpleName() + 
                     ";" + v.getCapacidadMaxima() + ";" + v.getTarifaBase());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Vehículo cargado: " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar vehículos: " + e.getMessage());
        }
    }
}
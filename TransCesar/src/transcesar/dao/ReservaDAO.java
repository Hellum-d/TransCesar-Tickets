package transcesar.dao;

import transcesar.model.Reserva;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private static final String ARCHIVO = "reservas.txt";
    private List<Reserva> reservas = new ArrayList<>();

    public ReservaDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Reserva r) {
        reservas.add(r);
        guardarEnArchivo(r);
    }

    public List<Reserva> listar() {
        return reservas;
    }

    private void guardarEnArchivo(Reserva r) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(
                r.getPasajero().getNombre() + ";" +
                r.getPasajero().getIdentificacion() + ";" +
                r.getVehiculo().getPlaca() + ";" +
                r.getFechaReserva() + ";" +
                r.getEstado()
            );
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Reserva cargada: " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar reservas: " + e.getMessage());
        }
    }
}
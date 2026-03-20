package transcesar.dao;

import transcesar.model.Ticket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private static final String ARCHIVO = "tickets.txt";
    private List<Ticket> tickets = new ArrayList<>();

    public TicketDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Ticket ticket) {
        tickets.add(ticket);
        guardarEnArchivo(ticket);
    }

    public List<Ticket> listar() {
        return tickets;
    }

    private void guardarEnArchivo(Ticket ticket) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(
                ticket.getPasajero().getNombre() + ";" +
                ticket.getPasajero().getIdentificacion() + ";" +
                ticket.getVehiculo().getPlaca() + ";" +
                ticket.getFechaCompra() + ";" +
                ticket.calcularTotal()
            );
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar ticket: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Ticket cargado: " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tickets: " + e.getMessage());
        }
    }
}
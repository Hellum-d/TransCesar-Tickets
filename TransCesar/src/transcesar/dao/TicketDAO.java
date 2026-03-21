package transcesar.dao;

import transcesar.model.*;
import java.io.*;
import java.time.LocalDate;
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
                ticket.getPasajero().getFechaNacimiento() + ";" +
                ticket.getPasajero().getClass().getSimpleName() + ";" +
                ticket.getVehiculo().getPlaca() + ";" +
                ticket.getVehiculo().getClass().getSimpleName() + ";" +
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
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length < 8) continue;

                String nombre = d[0];
                String identificacion = d[1];
                LocalDate fechaNacPasajero = LocalDate.parse(d[2]);
                String tipoPasajero = d[3];
                String placaVehiculo = d[4];
                String tipoVehiculo = d[5];
                LocalDate fechaCompra = LocalDate.parse(d[6]);
                double valorFinal = Double.parseDouble(d[7]);

                Pasajero pasajero;
                switch (tipoPasajero) {
                    case "PasajeroEstudiante":
                        pasajero = new PasajeroEstudiante(nombre, identificacion, fechaNacPasajero);
                        break;
                    case "PasajeroAdultoMayor":
                        pasajero = new PasajeroAdultoMayor(nombre, identificacion, fechaNacPasajero);
                        break;
                    default:
                        pasajero = new PasajeroRegular(nombre, identificacion, fechaNacPasajero);
                }

                Ruta rutaPlaceholder = new Ruta("??", "??", "??", 0, 0);
                Vehiculo vehiculo;
                switch (tipoVehiculo) {
                    case "Buseta":
                        vehiculo = new Buseta(placaVehiculo, rutaPlaceholder);
                        break;
                    case "MicroBus":
                        vehiculo = new MicroBus(placaVehiculo, rutaPlaceholder);
                        break;
                    default:
                        vehiculo = new Bus(placaVehiculo, rutaPlaceholder);
                }

                Ticket ticket = new Ticket(vehiculo, pasajero, valorFinal, fechaCompra);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tickets: " + e.getMessage());
        }
    }
}

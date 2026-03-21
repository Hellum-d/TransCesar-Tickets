package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;
import transcesar.model.Pasajero;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();
    private List<LocalDate> festivos = new ArrayList<>();

    public TicketService() {
        festivos.add(LocalDate.of(2026, 1, 1));
        festivos.add(LocalDate.of(2026, 1, 12));
        festivos.add(LocalDate.of(2026, 3, 23));
        festivos.add(LocalDate.of(2026, 4, 2));
        festivos.add(LocalDate.of(2026, 4, 3));
        festivos.add(LocalDate.of(2026, 5, 1));
        festivos.add(LocalDate.of(2026, 6, 15));
        festivos.add(LocalDate.of(2026, 6, 29));
        festivos.add(LocalDate.of(2026, 7, 20));
        festivos.add(LocalDate.of(2026, 8, 7));
        festivos.add(LocalDate.of(2026, 8, 17));
        festivos.add(LocalDate.of(2026, 10, 12));
        festivos.add(LocalDate.of(2026, 11, 2));
        festivos.add(LocalDate.of(2026, 11, 16));
        festivos.add(LocalDate.of(2026, 12, 8));
        festivos.add(LocalDate.of(2026, 12, 25));
    }

    public int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public int contarTicketsHoy(Pasajero pasajero) {
        int contador = 0;
        for (Ticket ticket : ticketDAO.listar()) {
            if (ticket.getPasajero().getIdentificacion().equals(pasajero.getIdentificacion()) &&
                ticket.getFechaCompra().equals(LocalDate.now())) {
                contador++;
            }
        }
        return contador;
    }

    public boolean venderTicket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase) {
        if (vehiculo.getPasajerosActuales() >= vehiculo.getCapacidadMaxima()) {
            System.out.println("No hay cupos disponibles en el vehículo " + vehiculo.getPlaca() + ".");
            return false;
        }
        int ticketsHoy = contarTicketsHoy(pasajero);
        if (ticketsHoy >= 3) {
            System.out.println("Venta rechazada: " + pasajero.getNombre() +
                " ya tiene " + ticketsHoy + " tickets comprados hoy.");
            return false;
        }
        if (festivos.contains(LocalDate.now())) {
            tarifaBase = tarifaBase * 1.20;
            System.out.println("Hoy es festivo. Tarifa aumentada 20% -> $" + String.format("%.0f", tarifaBase));
        }
        Ticket ticket = new Ticket(vehiculo, pasajero, tarifaBase);
        ticketDAO.guardar(ticket);
        vehiculo.setPasajerosActuales(vehiculo.getPasajerosActuales() + 1);
        System.out.println("Ticket vendido correctamente. Valor final: $" +
            String.format("%.0f", ticket.calcularTotal()));
        return true;
    }

    public List<Ticket> listarTickets() {
        return ticketDAO.listar();
    }

    public double calcularRecaudoTotal() {
        double total = 0;
        for (Ticket ticket : ticketDAO.listar()) {
            total += ticket.calcularTotal();
        }
        return total;
    }

    public int contarTotalTickets() {
        return ticketDAO.listar().size();
    }

    public void contarPasajerosPorTipo() {
        int regulares = 0, estudiantes = 0, adultos = 0;
        for (Ticket ticket : ticketDAO.listar()) {
            String tipo = ticket.getPasajero().getClass().getSimpleName();
            if (tipo.equals("PasajeroRegular")) regulares++;
            else if (tipo.equals("PasajeroEstudiante")) estudiantes++;
            else if (tipo.equals("PasajeroAdultoMayor")) adultos++;
        }
        System.out.println("Regulares    : " + regulares);
        System.out.println("Estudiantes  : " + estudiantes);
        System.out.println("Adulto Mayor : " + adultos);
    }

    public Vehiculo vehiculoMasUsado() {
        Vehiculo vehiculoTop = null;
        int max = 0;
        java.util.Map<String, Integer> conteo = new java.util.HashMap<>();
        java.util.Map<String, Vehiculo> mapa = new java.util.HashMap<>();
        for (Ticket ticket : ticketDAO.listar()) {
            String placa = ticket.getVehiculo().getPlaca();
            conteo.put(placa, conteo.getOrDefault(placa, 0) + 1);
            mapa.put(placa, ticket.getVehiculo());
        }
        for (java.util.Map.Entry<String, Integer> e : conteo.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                vehiculoTop = mapa.get(e.getKey());
            }
        }
        return vehiculoTop;
    }
}

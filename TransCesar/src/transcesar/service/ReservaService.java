package transcesar.service;

import transcesar.dao.ReservaDAO;
import transcesar.dao.TicketDAO;
import transcesar.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    private List<LocalDate> festivos = new ArrayList<>();
    private int contadorCodigo = 1;

    public ReservaService() {
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
        // Inicializar contador desde reservas existentes
        for (Reserva r : reservaDAO.listar()) {
            try {
                int num = Integer.parseInt(r.getCodigo().replace("RES-", ""));
                if (num >= contadorCodigo) contadorCodigo = num + 1;
            } catch (Exception e) {}
        }
    }

    private String generarCodigo() {
        return "RES-" + String.format("%03d", contadorCodigo++);
    }

    public boolean hacerReserva(Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje) {
        cancelarReservasVencidas();

        int ocupacionTotal = vehiculo.getPasajerosActuales() + contarReservasActivas(vehiculo);
        if (ocupacionTotal >= vehiculo.getCapacidadMaxima()) {
            System.out.println("No hay cupos disponibles para reservar en " + vehiculo.getPlaca());
            return false;
        }

        if (tieneReservaDuplicada(pasajero, vehiculo, fechaViaje)) {
            System.out.println("El pasajero ya tiene una reserva activa en este vehículo para esa fecha.");
            return false;
        }

        String codigo = generarCodigo();
        Reserva reserva = new Reserva(codigo, pasajero, vehiculo, fechaViaje);
        reservaDAO.guardar(reserva);
        System.out.println("Reserva creada correctamente. Código: " + codigo);
        return true;
    }

    public boolean cancelarPorCodigo(String codigo) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getCodigo().equals(codigo) && r.getEstado() == Reserva.Estado.ACTIVA) {
                r.setEstado(Reserva.Estado.CANCELADA);
                reservaDAO.actualizarArchivo();
                System.out.println("Reserva " + codigo + " cancelada correctamente.");
                return true;
            }
        }
        System.out.println("No se encontró reserva activa con código " + codigo);
        return false;
    }

    public boolean convertirATicket(String codigo) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getCodigo().equals(codigo) && r.getEstado() == Reserva.Estado.ACTIVA) {
                if (r.estaVencida()) {
                    r.setEstado(Reserva.Estado.CANCELADA);
                    reservaDAO.actualizarArchivo();
                    System.out.println("Reserva vencida. No se puede convertir.");
                    return false;
                }
                // Calcular tarifa
                double tarifa;
                Vehiculo v = r.getVehiculo();
                if (v instanceof Buseta) tarifa = ((Buseta) v).getTarifaBase();
                else if (v instanceof MicroBus) tarifa = ((MicroBus) v).getTarifaBase();
                else tarifa = ((Bus) v).getTarifaBase();

                // Aplicar festivo
                if (festivos.contains(LocalDate.now())) {
                    tarifa *= 1.20;
                    System.out.println("Hoy es festivo. Tarifa aumentada 20% -> $" + String.format("%.0f", tarifa));
                }

                // Crear ticket
                Ticket ticket = new Ticket(v, r.getPasajero(), tarifa);
                ticketDAO.guardar(ticket);
                v.setPasajerosActuales(v.getPasajerosActuales() + 1);

                // Cambiar estado reserva
                r.setEstado(Reserva.Estado.CONVERTIDA);
                reservaDAO.actualizarArchivo();

                System.out.println("Reserva convertida a ticket correctamente.");
                System.out.println("Valor final: $" + String.format("%.0f", ticket.calcularTotal()));
                return true;
            }
        }
        System.out.println("No se encontró reserva activa con código " + codigo);
        return false;
    }

    public int cancelarReservasVencidas() {
        int canceladas = 0;
        for (Reserva r : reservaDAO.listar()) {
            if (r.estaVencida()) {
                r.setEstado(Reserva.Estado.CANCELADA);
                canceladas++;
            }
        }
        if (canceladas > 0) reservaDAO.actualizarArchivo();
        return canceladas;
    }

    public List<Reserva> listarReservasActivas() {
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : reservaDAO.listar()) {
            if (r.getEstado() == Reserva.Estado.ACTIVA) activas.add(r);
        }
        return activas;
    }

    public List<Reserva> historialPasajero(String identificacion) {
        List<Reserva> historial = new ArrayList<>();
        for (Reserva r : reservaDAO.listar()) {
            if (r.getPasajero().getIdentificacion().equals(identificacion)) {
                historial.add(r);
            }
        }
        return historial;
    }

    public List<Reserva> listarReservas() {
        return reservaDAO.listar();
    }

    private int contarReservasActivas(Vehiculo vehiculo) {
        int contador = 0;
        for (Reserva r : reservaDAO.listar()) {
            if (r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()) &&
                r.getEstado() == Reserva.Estado.ACTIVA) {
                contador++;
            }
        }
        return contador;
    }

    private boolean tieneReservaDuplicada(Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getPasajero().getIdentificacion().equals(pasajero.getIdentificacion()) &&
                r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()) &&
                r.getFechaViaje().equals(fechaViaje) &&
                r.getEstado() == Reserva.Estado.ACTIVA) {
                return true;
            }
        }
        return false;
    }
}
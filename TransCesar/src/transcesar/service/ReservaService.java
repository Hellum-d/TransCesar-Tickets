package transcesar.service;

import transcesar.dao.ReservaDAO;
import transcesar.model.Reserva;
import transcesar.model.Pasajero;
import transcesar.model.Vehiculo;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();

    public boolean hacerReserva(Pasajero pasajero, Vehiculo vehiculo) {

        cancelarReservasVencidas();

        int ocupacionTotal = vehiculo.getPasajerosActuales() + contarReservasActivas(vehiculo);
        if (ocupacionTotal >= vehiculo.getCapacidadMaxima()) {
            System.out.println("No hay cupos disponibles para reservar.");
            return false;
        }

        if (tieneReservaDuplicada(pasajero, vehiculo)) {
            System.out.println("El pasajero ya tiene una reserva activa en este vehículo.");
            return false;
        }

        Reserva reserva = new Reserva(pasajero, vehiculo);
        reservaDAO.guardar(reserva);
        System.out.println("Reserva realizada correctamente.");
        return true;
    }

    public boolean cancelarReserva(Pasajero pasajero, Vehiculo vehiculo) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getPasajero().getIdentificacion().equals(pasajero.getIdentificacion()) &&
                r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()) &&
                r.getEstado() == Reserva.Estado.ACTIVA) {

                r.setEstado(Reserva.Estado.CANCELADA);
                System.out.println("Reserva cancelada correctamente.");
                return true;
            }
        }
        System.out.println("No se encontró una reserva activa para este pasajero.");
        return false;
    }

    public boolean tieneReservaActiva(Pasajero pasajero, Vehiculo vehiculo) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getPasajero().getIdentificacion().equals(pasajero.getIdentificacion()) &&
                r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()) &&
                r.getEstado() == Reserva.Estado.ACTIVA) {
                return true;
            }
        }
        return false;
    }

    public List<Reserva> listarReservas() {
        return reservaDAO.listar();
    }

    public void cancelarReservasVencidas() {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getEstado() == Reserva.Estado.ACTIVA) {

                long dias = ChronoUnit.DAYS.between(r.getFechaReserva(), LocalDate.now());

                if (dias >= 1) {
                    r.setEstado(Reserva.Estado.CANCELADA);
                    System.out.println("Reserva vencida cancelada automáticamente.");
                }
            }
        }
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

    private boolean tieneReservaDuplicada(Pasajero pasajero, Vehiculo vehiculo) {
        for (Reserva r : reservaDAO.listar()) {
            if (r.getPasajero().getIdentificacion().equals(pasajero.getIdentificacion()) &&
                r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()) &&
                r.getEstado() == Reserva.Estado.ACTIVA) {
                return true;
            }
        }
        return false;
    }
}
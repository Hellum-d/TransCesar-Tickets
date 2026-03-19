package transcesar.model;

import java.time.LocalDate;

public class Reserva implements Imprimible {

    public enum Estado { ACTIVA, CANCELADA }

    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaReserva;
    private Estado estado;

    public Reserva(Pasajero pasajero, Vehiculo vehiculo) {
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaReserva = LocalDate.now();
        this.estado = Estado.ACTIVA;
    }

    public Pasajero getPasajero() { return pasajero; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== RESERVA ===");
        System.out.println("Pasajero: " + pasajero.getNombre());
        System.out.println("Vehículo: " + vehiculo.getPlaca());
        System.out.println("Fecha: " + fechaReserva);
        System.out.println("Estado: " + estado);
    }
}
package transcesar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva implements Imprimible {

    public enum Estado { ACTIVA, CONVERTIDA, CANCELADA }

    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaViaje;
    private Estado estado;

    // Constructor para nueva reserva
    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaViaje = fechaViaje;
        this.estado = Estado.ACTIVA;
    }

    // Constructor para carga desde archivo
    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
                   LocalDateTime fechaCreacion, LocalDate fechaViaje, Estado estado) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.estado = estado;
    }

    public String getCodigo()             { return codigo; }
    public Pasajero getPasajero()         { return pasajero; }
    public Vehiculo getVehiculo()         { return vehiculo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDate getFechaViaje()      { return fechaViaje; }
    public Estado getEstado()             { return estado; }
    public void setEstado(Estado estado)  { this.estado = estado; }

    public boolean estaVencida() {
        return estado == Estado.ACTIVA &&
               LocalDateTime.now().isAfter(fechaCreacion.plusHours(24));
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== RESERVA ===");
        System.out.println("Código      : " + codigo);
        System.out.println("Pasajero    : " + pasajero.getNombre() + " (" + pasajero.getIdentificacion() + ")");
        System.out.println("Vehículo    : " + vehiculo.getPlaca() + " [" + vehiculo.getClass().getSimpleName() + "]");
        System.out.println("Fecha viaje : " + fechaViaje);
        System.out.println("Creada el   : " + fechaCreacion);
        System.out.println("Estado      : " + estado);
        if (estaVencida()) System.out.println("*** VENCIDA (más de 24h sin convertir) ***");
        System.out.println("----------------------------");
    }
}

package transcesar.model;

import java.time.LocalDate;

public class Ticket implements Calculable, Imprimible {

    private Vehiculo vehiculo;
    private Pasajero pasajero;
    private double tarifaBase;
    private LocalDate fechaCompra;

    public Ticket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase) {
        this.vehiculo = vehiculo;
        this.pasajero = pasajero;
        this.tarifaBase = tarifaBase;
        this.fechaCompra = LocalDate.now();
    }

    // Constructor para carga desde archivo (con fecha ya guardada)
    public Ticket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase, LocalDate fechaCompra) {
        this.vehiculo = vehiculo;
        this.pasajero = pasajero;
        this.tarifaBase = tarifaBase;
        this.fechaCompra = fechaCompra;
    }

    public Vehiculo getVehiculo() { return vehiculo; }
    public Pasajero getPasajero() { return pasajero; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public double getTarifaBase() { return tarifaBase; }

    @Override
    public double calcularTotal() {
        double descuento = pasajero.calcularDescuento();
        return tarifaBase - (tarifaBase * descuento);
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== TICKET ===");
        System.out.println("Pasajero    : " + pasajero.getNombre() + " (" + pasajero.getIdentificacion() + ")");
        System.out.println("Tipo        : " + pasajero.getClass().getSimpleName());
        System.out.println("Vehículo    : " + vehiculo.getPlaca() + " [" + vehiculo.getClass().getSimpleName() + "]");
        System.out.println("Fecha compra: " + fechaCompra);
        System.out.println("Valor final : $" + String.format("%.0f", calcularTotal()));
        System.out.println("----------------------------");
    }
}

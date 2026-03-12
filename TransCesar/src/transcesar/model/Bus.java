package transcesar.model;

public class Bus extends Vehiculo {
    private final double TARIFA_BASE = 15000.0;

    public Bus(String placa, String ruta) {
        super(placa, ruta, 45);
    }

    public double getTarifaBase() {
        return TARIFA_BASE;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("TIPO: BUS");
        System.out.println("Placa: " + placa + " | Ruta: " + ruta);
        System.out.println("Capacidad: " + capacidadMaxima + " | Pasajeros: " + pasajerosActuales);
        System.out.println("Tarifa: $" + TARIFA_BASE);
        System.out.println("Estado: " + (disponible ? "Disponible" : "Lleno"));
    }
}
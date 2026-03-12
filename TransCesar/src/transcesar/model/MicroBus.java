package transcesar.model;

public class MicroBus extends Vehiculo {
    private final double TARIFA_BASE = 10000.0;

    public MicroBus(String placa, String ruta) {
        super(placa, ruta, 25);
    }

    public double getTarifaBase() {
        return TARIFA_BASE;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("TIPO: MICROBÚS");
        System.out.println("Placa: " + placa + " | Ruta: " + ruta);
        System.out.println("Capacidad: " + capacidadMaxima + " | Pasajeros: " + pasajerosActuales);
        System.out.println("Tarifa: $" + TARIFA_BASE);
        System.out.println("Estado: " + (disponible ? "Disponible" : "Lleno"));
    }
}
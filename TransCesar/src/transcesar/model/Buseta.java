package transcesar.model;

public class Buseta extends Vehiculo {
    private final double TARIFA_BASE = 8000.0;

    public Buseta(String placa, String ruta) {
        super(placa, ruta, 19);
    }

    public double getTarifaBase() {
        return TARIFA_BASE;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("TIPO: BUSETA");
        System.out.println("Placa: " + placa + " | Ruta: " + ruta);
        System.out.println("Capacidad: " + capacidadMaxima + " | Pasajeros: " + pasajerosActuales);
        System.out.println("Tarifa: $" + TARIFA_BASE);
        System.out.println("Estado: " + (disponible ? "Disponible" : "Lleno"));
    }
}
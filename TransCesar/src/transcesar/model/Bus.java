package transcesar.model;

public class Bus extends Vehiculo {
    private final double TARIFA_BASE = 15000.0;

    public Bus(String placa, Ruta ruta) {
        super(placa, ruta, 45);
    }

    public double getTarifaBase() {
        return TARIFA_BASE;
    }

    @Override
  
public void imprimirDetalle() {
    System.out.println("------ REPORTE DE UNIDAD ------");
    System.out.println("TIPO: " + this.getClass().getSimpleName().toUpperCase());
    System.out.println("Placa: " + placa);
    
    System.out.println("RUTA ASIGNADA: " + ruta.getCodigo());
    System.out.println("Trayecto: " + ruta.getOrigen() + " -> " + ruta.getDestino());
    System.out.println("Distancia: " + ruta.getDistanciaKm() + " km");
    System.out.println("Tiempo Est.: " + ruta.getTiempoMinutos() + " min");
    
    System.out.println("Capacidad: " + capacidadMaxima + " | Pasajeros: " + pasajerosActuales);
    System.out.println("Estado: " + (disponible ? "Disponible" : "Lleno"));
    System.out.println("-------------------------------");
}
}
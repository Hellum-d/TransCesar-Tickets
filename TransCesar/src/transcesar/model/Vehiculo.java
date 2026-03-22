package transcesar.model;

public abstract class Vehiculo implements Imprimible {
    protected String placa;
    protected Ruta ruta; 
    protected int capacidadMaxima;
    protected int pasajerosActuales;
    protected boolean disponible;

    protected double tarifaBase;

    public Vehiculo(String placa, Ruta ruta, int capacidadMaxima) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.pasajerosActuales = 0;
        this.disponible = true;
        this.tarifaBase = 10000; 
    }

    public String getPlaca() {
        return placa;
    }

    public Ruta getRuta() {
        return ruta;
    } 

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getPasajerosActuales() {
        return pasajerosActuales;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public void setPasajerosActuales(int pasajerosActuales) {
        this.pasajerosActuales = pasajerosActuales;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public abstract void imprimirDetalle();
}
package transcesar.model;

public class Ruta {
    private String codigo;
    private String origen;
    private String destino;
    private double distanciaKm;
    private int tiempoMinutos;

    public Ruta(String codigo, String origen, String destino, double distanciaKm, int tiempoMinutos) {
        this.codigo = codigo; 
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.tiempoMinutos = tiempoMinutos; 
    }

    public String getCodigo() {
        return codigo;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public int getTiempoMinutos() {
        return tiempoMinutos;
    }
    
    
}
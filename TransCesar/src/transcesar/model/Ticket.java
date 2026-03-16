/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

public class Ticket {

    private Vehiculo vehiculo;
    private Pasajero pasajero;
    private double tarifaBase;

    public Ticket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase){
        this.vehiculo = vehiculo;
        this.pasajero = pasajero;
        this.tarifaBase = tarifaBase;
    }

    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public Pasajero getPasajero(){
        return pasajero;
    }

    public double calcularTotal(){
        double descuento = pasajero.calcularDescuento();
        return tarifaBase - (tarifaBase * descuento);
    }

}
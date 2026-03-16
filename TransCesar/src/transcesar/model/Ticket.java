/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

public class Ticket {

    private Pasajero pasajero;
    private double tarifaBase;

    public Ticket(Pasajero pasajero, double tarifaBase) {
        this.pasajero = pasajero;
        this.tarifaBase = tarifaBase;
    }

    public double calcularTotal() {

        double descuento = pasajero.calcularDescuento();

        return tarifaBase - (tarifaBase * descuento);
    }

    public Pasajero getPasajero() {
        return pasajero;
    }
}
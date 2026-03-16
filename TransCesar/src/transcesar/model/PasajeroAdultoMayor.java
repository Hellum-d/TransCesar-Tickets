/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

import java.time.LocalDate;

public class PasajeroAdultoMayor extends Pasajero {

    public PasajeroAdultoMayor(String nombre, String identificacion, LocalDate fechaNacimiento) {
        super(nombre, identificacion, fechaNacimiento);
    }

    @Override
    public double calcularDescuento() {
        return 0.3;
    }
}
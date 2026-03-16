package transcesar.model;

import java.time.LocalDate;

public abstract class Pasajero extends Persona {

    public Pasajero(String nombre, String identificacion, LocalDate fechaNacimiento) {
        super(nombre, identificacion, fechaNacimiento);
    }

    public abstract double calcularDescuento();
}
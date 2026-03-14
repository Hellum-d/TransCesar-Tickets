package transcesar.model;

import java.time.LocalDate; 

public class Pasajero {
    private String nombre;
    private String identificacion;
    private LocalDate fechaNacimiento; 

    public Pasajero(String nombre, String identificacion, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
    }

   
    public String getNombre() { 
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
}
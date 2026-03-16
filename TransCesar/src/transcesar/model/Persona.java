/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

import java.time.LocalDate;

public abstract class Persona {

    protected String nombre;
    protected String identificacion;
    protected LocalDate fechaNacimiento;

    public Persona(String nombre, String identificacion, LocalDate fechaNacimiento) {
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
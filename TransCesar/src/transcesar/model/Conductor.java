/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.model;

import java.time.LocalDate;

public class Conductor extends Persona {

    private String licencia;

    public Conductor(String nombre, String identificacion, LocalDate fechaNacimiento, String licencia) {
        super(nombre, identificacion, fechaNacimiento);
        this.licencia = licencia;
    }

    public String getLicencia() {
        return licencia;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

import transcesar.model.Persona;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    private List<Persona> personas = new ArrayList<>();

    public void guardar(Persona persona){
        personas.add(persona);
    }

    public List<Persona> listar(){
        return personas;
    }

}
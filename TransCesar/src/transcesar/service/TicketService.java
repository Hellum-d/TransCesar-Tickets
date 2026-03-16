/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;
import transcesar.model.Pasajero;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();

    public boolean venderTicket(Vehiculo vehiculo, Pasajero pasajero, double precioBase){

       
        if(vehiculo.getPasajerosActuales() >= vehiculo.getCapacidadMaxima()){
            System.out.println("No hay cupos disponibles");
            return false;
        }

    
        Ticket ticket = new Ticket(vehiculo, pasajero, precioBase);

        
        ticketDAO.guardar(ticket);

        
        vehiculo.setPasajerosActuales(vehiculo.getPasajerosActuales() + 1);

        System.out.println("Ticket vendido correctamente");

        return true;
    }

}
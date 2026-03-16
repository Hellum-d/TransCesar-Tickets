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

    public boolean venderTicket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase){

        if(vehiculo.getPasajerosActuales() >= vehiculo.getCapacidadMaxima()){
            System.out.println("No hay cupos disponibles");
            return false;
        }

        Ticket ticket = new Ticket(vehiculo, pasajero, tarifaBase);

        ticketDAO.guardar(ticket);

        vehiculo.setPasajerosActuales(vehiculo.getPasajerosActuales() + 1);

        System.out.println("Ticket vendido correctamente");

        return true;
    }

    public double calcularRecaudoTotal(){

        double total = 0;

        for(Ticket ticket : ticketDAO.listar()){
            total += ticket.calcularTotal();
        }

        return total;
    }

    public void contarPasajerosPorTipo(){

        int regulares = 0;
        int estudiantes = 0;
        int adultos = 0;

        for(Ticket ticket : ticketDAO.listar()){

            String tipo = ticket.getPasajero().getClass().getSimpleName();

            if(tipo.equals("PasajeroRegular")){
                regulares++;
            }

            if(tipo.equals("PasajeroEstudiante")){
                estudiantes++;
            }

            if(tipo.equals("PasajeroAdultoMayor")){
                adultos++;
            }
        }

        System.out.println("Pasajeros regulares: " + regulares);
        System.out.println("Pasajeros estudiantes: " + estudiantes);
        System.out.println("Pasajeros adulto mayor: " + adultos);
    }

    public Vehiculo vehiculoMasUsado(){

        Vehiculo vehiculoTop = null;
        int max = 0;

        for(Ticket ticket : ticketDAO.listar()){

            Vehiculo v = ticket.getVehiculo();

            if(v.getPasajerosActuales() > max){
                max = v.getPasajerosActuales();
                vehiculoTop = v;
            }
        }

        return vehiculoTop;
    }

}
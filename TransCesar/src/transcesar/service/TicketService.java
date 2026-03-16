/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;
import transcesar.model.Pasajero;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();
    private List<LocalDate> festivos = new ArrayList<>();

    public TicketService(){

        festivos.add(LocalDate.of(2026,1,1));
        festivos.add(LocalDate.of(2026,5,1));
        festivos.add(LocalDate.of(2026,7,20));
        festivos.add(LocalDate.of(2026,12,25));

    }

    public int calcularEdad(LocalDate fechaNacimiento){

        LocalDate hoy = LocalDate.now();

        return Period.between(fechaNacimiento, hoy).getYears();
    }

    public int contarTicketsHoy(Pasajero pasajero){

        int contador = 0;

        for(Ticket ticket : ticketDAO.listar()){

            if(ticket.getPasajero().equals(pasajero)){
                contador++;
            }
        }

        return contador;
    }

    public boolean venderTicket(Vehiculo vehiculo, Pasajero pasajero, double tarifaBase){

        if(vehiculo.getPasajerosActuales() >= vehiculo.getCapacidadMaxima()){
            System.out.println("No hay cupos disponibles");
            return false;
        }

        if(contarTicketsHoy(pasajero) >= 3){
            System.out.println("El pasajero ya compró 3 tickets hoy");
            return false;
        }

        if(festivos.contains(LocalDate.now())){
            tarifaBase = tarifaBase * 1.20;
            System.out.println("Hoy es festivo, tarifa aumentada 20%");
        }

        int edad = calcularEdad(pasajero.getFechaNacimiento());

        if(edad >= 60){
            tarifaBase = tarifaBase * 0.70;
            System.out.println("Descuento aplicado por adulto mayor");
        }

        Ticket ticket = new Ticket(vehiculo, pasajero, tarifaBase);

        ticketDAO.guardar(ticket);

        vehiculo.setPasajerosActuales(
            vehiculo.getPasajerosActuales() + 1
        );

        System.out.println("Ticket vendido correctamente");

        return true;
    }

}
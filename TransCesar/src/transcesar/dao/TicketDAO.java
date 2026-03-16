/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.dao;

import transcesar.model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private List<Ticket> tickets = new ArrayList<>();

    public void guardar(Ticket ticket){
        tickets.add(ticket);
    }

    public List<Ticket> listar(){
        return tickets;
    }

}
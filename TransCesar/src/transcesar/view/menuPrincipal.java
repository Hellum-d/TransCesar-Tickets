/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transcesar.view;


import java.util.Scanner;

public class menuPrincipal {
      Scanner sc = new Scanner(System.in);

    public void mostrarMenu() { 
        int opcion;
        do {
            System.out.println("\n===== TRANSCESAR S.A.S. =====");
            System.out.println("1. Gestión de Vehículos");
            System.out.println("2. Gestión de Conductores");
            System.out.println("3. Gestión de Pasajeros");
            System.out.println("4. Venta de Tickets");
            System.out.println("5. Reportes y Estadísticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: System.out.println(">> Módulo de Vehículos (en construcción)"); break;
                case 2: System.out.println(">> Módulo de Conductores (en construcción)"); break;
                case 3: System.out.println(">> Módulo de Pasajeros (en construcción)"); break;
                case 4: System.out.println(">> Módulo de Tickets (en construcción)"); break;
                case 5: System.out.println(">> Módulo de Reportes (en construcción)"); break;
                case 0: System.out.println("Cerrando sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}

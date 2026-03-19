package transcesar.view;

import transcesar.model.*;
import transcesar.service.VehiculoService;
import transcesar.service.TicketService;
import transcesar.service.ReservaService;
import transcesar.dao.TicketDAO;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class menuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private VehiculoService vehiculoService = new VehiculoService();
    private TicketService ticketService = new TicketService();
    private ReservaService reservaService = new ReservaService();
    private TicketDAO ticketDAO = new TicketDAO();

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== TRANSCESAR S.A.S. =====");
            System.out.println("1. Gestión de Vehículos");
            System.out.println("2. Gestión de Conductores");
            System.out.println("3. Gestión de Pasajeros");
            System.out.println("4. Venta de Tickets");
            System.out.println("5. Reportes y Estadísticas");
            System.out.println("6. Gestión de Reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: menuVehiculos(); break;
                case 2: System.out.println(">> Módulo de Conductores (en construcción)"); break;
                case 3: System.out.println(">> Módulo de Pasajeros (en construcción)"); break;
                case 4: menuVentaTickets(); break;
                case 5: menuReportes(); break;
                case 6: menuReservas(); break;
                case 0: System.out.println("Cerrando sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuVehiculos() {
        System.out.println("\n--- GESTIÓN DE VEHÍCULOS ---");
        System.out.println("1. Registrar Buseta");
        System.out.println("2. Registrar MicroBus");
        System.out.println("3. Registrar Bus");
        System.out.println("4. Listar vehículos");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 4) {
            List<Vehiculo> lista = vehiculoService.consultarVehiculos();
            if (lista.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
            } else {
                for (Vehiculo v : lista) {
                    v.imprimirDetalle();
                }
            }
            return;
        }

        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Código de ruta: ");
        String codigoRuta = scanner.nextLine();
        System.out.print("Ciudad origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ciudad destino: ");
        String destino = scanner.nextLine();
        System.out.print("Distancia (km): ");
        double distancia = scanner.nextDouble();
        System.out.print("Tiempo estimado (min): ");
        int tiempo = scanner.nextInt();
        scanner.nextLine();

        Ruta ruta = new Ruta(codigoRuta, origen, destino, distancia, tiempo);
        Vehiculo v = null;

        switch (op) {
            case 1: v = new Buseta(placa, ruta); break;
            case 2: v = new MicroBus(placa, ruta); break;
            case 3: v = new Bus(placa, ruta); break;
            default: System.out.println("Opción no válida."); return;
        }

        vehiculoService.registrarVehiculo(v);
    }

    private void menuVentaTickets() {
        System.out.println("\n--- VENTA DE TICKETS ---");

        List<Vehiculo> vehiculos = vehiculoService.consultarVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println(i + ". " + vehiculos.get(i).getPlaca());
        }
        System.out.print("Seleccione vehículo: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        Vehiculo vehiculo = vehiculos.get(idx);

        System.out.print("Nombre pasajero: ");
        String nombre = scanner.nextLine();
        System.out.print("Identificación: ");
        String id = scanner.nextLine();
        System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNac = LocalDate.parse(scanner.nextLine());

        System.out.println("Tipo de pasajero:");
        System.out.println("1. Regular");
        System.out.println("2. Estudiante");
        System.out.print("Seleccione: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Pasajero pasajero;
        switch (tipo) {
            case 2: pasajero = new PasajeroEstudiante(nombre, id, fechaNac); break;
            default: pasajero = new PasajeroRegular(nombre, id, fechaNac); break;
        }

        System.out.print("Tarifa base: ");
        double tarifa = scanner.nextDouble();
        scanner.nextLine();

        ticketService.venderTicket(vehiculo, pasajero, tarifa);
    }

    private void menuReportes() {
        System.out.println("\n--- REPORTES Y ESTADÍSTICAS ---");
        System.out.println("1. Listar todos los tickets");
        System.out.println("2. Total recaudado");
        System.out.println("3. Pasajeros por tipo");
        System.out.println("4. Vehículo más usado");
        System.out.println("5. Resumen del día");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        switch (op) {
            case 1:
                List<Ticket> tickets = ticketDAO.listar();
                if (tickets.isEmpty()) {
                    System.out.println("No hay tickets registrados.");
                } else {
                    for (Ticket t : tickets) {
                        t.imprimirDetalle();
                    }
                }
                break;
            case 2:
                System.out.println("Total recaudado: $" + ticketService.calcularRecaudoTotal());
                break;
            case 3:
                ticketService.contarPasajerosPorTipo();
                break;
            case 4:
                Vehiculo top = ticketService.vehiculoMasUsado();
                if (top != null) {
                    top.imprimirDetalle();
                } else {
                    System.out.println("No hay datos suficientes.");
                }
                break;
            case 5:
                System.out.println("=== RESUMEN DEL DÍA ===");
                System.out.println("Fecha: " + LocalDate.now());
                System.out.println("Total tickets: " + ticketDAO.listar().size());
                System.out.println("Total recaudado: $" + ticketService.calcularRecaudoTotal());
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void menuReservas() {
        System.out.println("\n--- GESTIÓN DE RESERVAS ---");
        System.out.println("1. Hacer reserva");
        System.out.println("2. Cancelar reserva");
        System.out.println("3. Listar reservas");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 3) {
            List<Reserva> reservas = reservaService.listarReservas();
            if (reservas.isEmpty()) {
                System.out.println("No hay reservas registradas.");
            } else {
                for (Reserva r : reservas) {
                    r.imprimirDetalle();
                }
            }
            return;
        }

        List<Vehiculo> vehiculos = vehiculoService.consultarVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println(i + ". " + vehiculos.get(i).getPlaca());
        }
        System.out.print("Seleccione vehículo: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        Vehiculo vehiculo = vehiculos.get(idx);

        System.out.print("Identificación del pasajero: ");
        String id = scanner.nextLine();
        System.out.print("Nombre del pasajero: ");
        String nombre = scanner.nextLine();
        System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNac = LocalDate.parse(scanner.nextLine());
        Pasajero pasajero = new PasajeroRegular(nombre, id, fechaNac);

        switch (op) {
            case 1: reservaService.hacerReserva(pasajero, vehiculo); break;
            case 2: reservaService.cancelarReserva(pasajero, vehiculo); break;
            default: System.out.println("Opción no válida.");
        }
    }
}
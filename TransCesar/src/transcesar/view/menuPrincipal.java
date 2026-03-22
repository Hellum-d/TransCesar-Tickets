package transcesar.view;

import transcesar.model.*;
import transcesar.service.VehiculoService;
import transcesar.service.TicketService;
import transcesar.service.ReservaService;
import transcesar.service.PersonaService;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;

public class menuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private VehiculoService vehiculoService = new VehiculoService();
    private TicketService ticketService = new TicketService();
    private ReservaService reservaService = new ReservaService();
    private PersonaService personaService = new PersonaService();

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
                case 2: menuConductores(); break;
                case 3: menuPasajeros(); break;
                case 4: menuVentaTickets(); break;
                case 5: menuReportes(); break;
                case 6: menuReservas(); break;
                case 0: System.out.println("Cerrando sistema..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private LocalDate leerFechaNacimiento() {
        while (true) {
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            String entrada = scanner.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(entrada);
                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("Error: la fecha no puede ser futura. Intente de nuevo.");
                    continue;
                }
                if (fecha.isBefore(LocalDate.of(1900, 1, 1))) {
                    System.out.println("Error: fecha inválida (muy antigua). Intente de nuevo.");
                    continue;
                }
                return fecha;
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use YYYY-MM-DD (ej: 1990-05-20).");
            }
        }
    }

    private LocalDate leerFechaViaje() {
        while (true) {
            System.out.print("Fecha del viaje (YYYY-MM-DD): ");
            String entrada = scanner.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(entrada);
                if (fecha.isBefore(LocalDate.now())) {
                    System.out.println("Error: la fecha del viaje no puede ser pasada. Intente de nuevo.");
                    continue;
                }
                return fecha;
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use YYYY-MM-DD (ej: 2026-03-25).");
            }
        }
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
                for (Vehiculo v : lista) v.imprimirDetalle();
            }
            return;
        }

        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim().toUpperCase();
        System.out.print("Código de ruta: ");
        String codigoRuta = scanner.nextLine().trim();
        System.out.print("Ciudad origen: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Ciudad destino: ");
        String destino = scanner.nextLine().trim();
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

    private void menuConductores() {
        System.out.println("\n--- GESTIÓN DE CONDUCTORES ---");
        System.out.println("1. Registrar conductor");
        System.out.println("2. Listar conductores");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 2) {
            List<Conductor> lista = personaService.listarConductores();
            if (lista.isEmpty()) {
                System.out.println("No hay conductores registrados.");
            } else {
                for (Conductor c : lista) {
                    System.out.println("-----------------------------");
                    System.out.println("Nombre        : " + c.getNombre());
                    System.out.println("Identificación: " + c.getIdentificacion());
                    System.out.println("Nacimiento    : " + c.getFechaNacimiento());
                    System.out.println("Licencia      : " + c.getLicencia());
                }
                System.out.println("-----------------------------");
            }
            return;
        }

        if (op == 1) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Identificación (cédula): ");
            String id = scanner.nextLine().trim();
            LocalDate fechaNac = leerFechaNacimiento();
            System.out.println("Categoría de licencia (B1 / B2 / C1 / C2): ");
            System.out.print("Seleccione: ");
            String licencia = scanner.nextLine().trim().toUpperCase();
            Conductor c = new Conductor(nombre, id, fechaNac, licencia);
            personaService.registrarConductor(c);
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void menuPasajeros() {
        System.out.println("\n--- GESTIÓN DE PASAJEROS ---");
        System.out.println("1. Registrar pasajero");
        System.out.println("2. Listar pasajeros");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 2) {
            List<Pasajero> lista = personaService.listarPasajeros();
            if (lista.isEmpty()) {
                System.out.println("No hay pasajeros registrados.");
            } else {
                for (Pasajero p : lista) {
                    int edad = Period.between(p.getFechaNacimiento(), LocalDate.now()).getYears();
                    System.out.println("-----------------------------");
                    System.out.println("Nombre        : " + p.getNombre());
                    System.out.println("Identificación: " + p.getIdentificacion());
                    System.out.println("Edad          : " + edad + " años");
                    System.out.println("Tipo          : " + p.getClass().getSimpleName());
                    System.out.println("Descuento     : " + (int)(p.calcularDescuento() * 100) + "%");
                }
                System.out.println("-----------------------------");
            }
            return;
        }

        if (op == 1) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Identificación (cédula): ");
            String id = scanner.nextLine().trim();
            LocalDate fechaNac = leerFechaNacimiento();
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            Pasajero p;
            if (edad >= 60) {
                p = new PasajeroAdultoMayor(nombre, id, fechaNac);
                System.out.println("Categoría asignada automáticamente: Adulto Mayor (descuento 30%)");
            } else {
                System.out.println("Tipo de pasajero:");
                System.out.println("1. Regular (sin descuento)");
                System.out.println("2. Estudiante (descuento 15%)");
                System.out.print("Seleccione: ");
                int tipo = scanner.nextInt();
                scanner.nextLine();
                p = (tipo == 2)
                    ? new PasajeroEstudiante(nombre, id, fechaNac)
                    : new PasajeroRegular(nombre, id, fechaNac);
            }
            personaService.registrarPasajero(p);
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void menuVentaTickets() {
        System.out.println("\n--- VENTA DE TICKETS ---");
        List<Vehiculo> vehiculos = vehiculoService.consultarVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados. Registre uno primero.");
            return;
        }
        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            System.out.println(i + ". " + v.getPlaca() +
                " [" + v.getClass().getSimpleName() + "]" +
                " | Cupos: " + (v.getCapacidadMaxima() - v.getPasajerosActuales()) + " disponibles");
        }
        System.out.print("Seleccione vehículo (número): ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx < 0 || idx >= vehiculos.size()) {
            System.out.println("Número de vehículo inválido.");
            return;
        }
        Vehiculo vehiculo = vehiculos.get(idx);
        System.out.print("Nombre pasajero: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Identificación: ");
        String id = scanner.nextLine().trim();
        LocalDate fechaNac = leerFechaNacimiento();
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        Pasajero pasajero;
        if (edad >= 60) {
            pasajero = new PasajeroAdultoMayor(nombre, id, fechaNac);
            System.out.println("Pasajero identificado como Adulto Mayor (descuento 30% automático).");
        } else {
            System.out.println("Tipo de pasajero:");
            System.out.println("1. Regular (sin descuento)");
            System.out.println("2. Estudiante (descuento 15%)");
            System.out.println("3. Adulto Mayor (descuento 30%)");
            System.out.print("Seleccione: ");
            int tipo = scanner.nextInt();
            scanner.nextLine();
            switch (tipo) {
                case 2:  pasajero = new PasajeroEstudiante(nombre, id, fechaNac); break;
                case 3:  pasajero = new PasajeroAdultoMayor(nombre, id, fechaNac); break;
                default: pasajero = new PasajeroRegular(nombre, id, fechaNac); break;
            }
        }
        double tarifa;
        if (vehiculo instanceof Buseta) tarifa = ((Buseta) vehiculo).getTarifaBase();
        else if (vehiculo instanceof MicroBus) tarifa = ((MicroBus) vehiculo).getTarifaBase();
        else tarifa = ((Bus) vehiculo).getTarifaBase();
        System.out.println("Tarifa base del vehículo: $" + String.format("%.0f", tarifa));
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
                List<Ticket> tickets = ticketService.listarTickets();
                if (tickets.isEmpty()) {
                    System.out.println("No hay tickets registrados.");
                } else {
                    System.out.println("Total de tickets: " + tickets.size());
                    for (Ticket t : tickets) t.imprimirDetalle();
                }
                break;
            case 2:
                System.out.println("Total de tickets vendidos: " + ticketService.contarTotalTickets());
                System.out.println("Total recaudado          : $" + String.format("%.0f", ticketService.calcularRecaudoTotal()));
                break;
            case 3:
                ticketService.contarPasajerosPorTipo();
                break;
            case 4:
                Vehiculo top = ticketService.vehiculoMasUsado();
                if (top != null) top.imprimirDetalle();
                else System.out.println("No hay datos suficientes.");
                break;
            case 5:
                int totalHoy = 0;
                double recaudoHoy = 0;
                for (Ticket t : ticketService.listarTickets()) {
                    if (t.getFechaCompra().equals(LocalDate.now())) {
                        totalHoy++;
                        recaudoHoy += t.calcularTotal();
                    }
                }
                System.out.println("=== RESUMEN DEL DÍA ===");
                System.out.println("Fecha           : " + LocalDate.now());
                System.out.println("Tickets vendidos: " + totalHoy);
                System.out.println("Total recaudado : $" + String.format("%.0f", recaudoHoy));
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void menuReservas() {
        System.out.println("\n--- GESTIÓN DE RESERVAS ---");
        System.out.println("1. Hacer reserva");
        System.out.println("2. Cancelar reserva por código");
        System.out.println("3. Listar reservas activas");
        System.out.println("4. Historial de reservas de un pasajero");
        System.out.println("5. Convertir reserva en ticket");
        System.out.println("6. Verificar reservas vencidas");
        System.out.print("Seleccione: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        switch (op) {
            case 1:
                List<Vehiculo> vehiculos = vehiculoService.consultarVehiculos();
                if (vehiculos.isEmpty()) {
                    System.out.println("No hay vehículos registrados.");
                    return;
                }
                System.out.println("Vehículos disponibles:");
                for (int i = 0; i < vehiculos.size(); i++) {
                    Vehiculo v = vehiculos.get(i);
                    System.out.println(i + ". " + v.getPlaca() +
                        " [" + v.getClass().getSimpleName() + "]" +
                        " | Cupos: " + (v.getCapacidadMaxima() - v.getPasajerosActuales()) + " disponibles");
                }
                System.out.print("Seleccione vehículo: ");
                int idx = scanner.nextInt();
                scanner.nextLine();
                if (idx < 0 || idx >= vehiculos.size()) {
                    System.out.println("Número inválido.");
                    return;
                }
                Vehiculo vehiculo = vehiculos.get(idx);
                System.out.print("Nombre del pasajero: ");
                String nombre = scanner.nextLine().trim();
                System.out.print("Identificación: ");
                String id = scanner.nextLine().trim();
                LocalDate fechaNac = leerFechaNacimiento();
                int edad = Period.between(fechaNac, LocalDate.now()).getYears();
                Pasajero pasajero;
                if (edad >= 60) {
                    pasajero = new PasajeroAdultoMayor(nombre, id, fechaNac);
                } else {
                    System.out.println("Tipo: 1. Regular  2. Estudiante");
                    System.out.print("Seleccione: ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();
                    pasajero = (tipo == 2)
                        ? new PasajeroEstudiante(nombre, id, fechaNac)
                        : new PasajeroRegular(nombre, id, fechaNac);
                }
                LocalDate fechaViaje = leerFechaViaje();
                reservaService.hacerReserva(pasajero, vehiculo, fechaViaje);
                break;

            case 2:
                System.out.print("Código de reserva (ej: RES-001): ");
                String codigo = scanner.nextLine().trim().toUpperCase();
                reservaService.cancelarPorCodigo(codigo);
                break;

            case 3:
                List<Reserva> activas = reservaService.listarReservasActivas();
                if (activas.isEmpty()) {
                    System.out.println("No hay reservas activas.");
                } else {
                    System.out.println("Reservas activas: " + activas.size());
                    for (Reserva r : activas) r.imprimirDetalle();
                }
                break;

            case 4:
                System.out.print("Identificación del pasajero: ");
                String cedula = scanner.nextLine().trim();
                List<Reserva> historial = reservaService.historialPasajero(cedula);
                if (historial.isEmpty()) {
                    System.out.println("No hay reservas para ese pasajero.");
                } else {
                    System.out.println("Historial de reservas (" + historial.size() + " en total):");
                    for (Reserva r : historial) r.imprimirDetalle();
                }
                break;

            case 5:
                System.out.print("Código de reserva a convertir (ej: RES-001): ");
                String codigoConvertir = scanner.nextLine().trim().toUpperCase();
                reservaService.convertirATicket(codigoConvertir);
                break;

            case 6:
                int canceladas = reservaService.cancelarReservasVencidas();
                if (canceladas == 0) {
                    System.out.println("No hay reservas vencidas.");
                } else {
                    System.out.println(canceladas + " reserva(s) vencida(s) cancelada(s) automáticamente.");
                }
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }
}
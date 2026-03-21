package transcesar.dao;

import transcesar.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private static final String ARCHIVO = "reservas.txt";
    private List<Reserva> reservas = new ArrayList<>();

    public ReservaDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Reserva r) {
        reservas.add(r);
        guardarEnArchivo(r);
    }

    public List<Reserva> listar() {
        return reservas;
    }

    // Reescribe el archivo completo (necesario al cambiar estados)
    public void actualizarArchivo() {
        try (FileWriter fw = new FileWriter(ARCHIVO, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Reserva r : reservas) {
                bw.write(serializar(r));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar reservas: " + e.getMessage());
        }
    }

    private String serializar(Reserva r) {
        return r.getCodigo() + ";" +
               r.getPasajero().getNombre() + ";" +
               r.getPasajero().getIdentificacion() + ";" +
               r.getPasajero().getFechaNacimiento() + ";" +
               r.getPasajero().getClass().getSimpleName() + ";" +
               r.getVehiculo().getPlaca() + ";" +
               r.getVehiculo().getClass().getSimpleName() + ";" +
               r.getFechaCreacion() + ";" +
               r.getFechaViaje() + ";" +
               r.getEstado();
    }

    private void guardarEnArchivo(Reserva r) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(serializar(r));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length < 10) continue;
                try {
                    String codigo        = d[0];
                    String nombre        = d[1];
                    String identificacion = d[2];
                    LocalDate fechaNacP  = LocalDate.parse(d[3]);
                    String tipoPasajero  = d[4];
                    String placa         = d[5];
                    String tipoVehiculo  = d[6];
                    LocalDateTime fechaCreacion = LocalDateTime.parse(d[7]);
                    LocalDate fechaViaje = LocalDate.parse(d[8]);
                    Reserva.Estado estado = Reserva.Estado.valueOf(d[9]);

                    Pasajero pasajero;
                    switch (tipoPasajero) {
                        case "PasajeroEstudiante":
                            pasajero = new PasajeroEstudiante(nombre, identificacion, fechaNacP); break;
                        case "PasajeroAdultoMayor":
                            pasajero = new PasajeroAdultoMayor(nombre, identificacion, fechaNacP); break;
                        default:
                            pasajero = new PasajeroRegular(nombre, identificacion, fechaNacP);
                    }

                    Ruta ruta = new Ruta("??", "??", "??", 0, 0);
                    Vehiculo vehiculo;
                    switch (tipoVehiculo) {
                        case "Buseta":   vehiculo = new Buseta(placa, ruta); break;
                        case "MicroBus": vehiculo = new MicroBus(placa, ruta); break;
                        default:         vehiculo = new Bus(placa, ruta);
                    }

                    reservas.add(new Reserva(codigo, pasajero, vehiculo,
                                             fechaCreacion, fechaViaje, estado));
                } catch (Exception e) {
                    System.out.println("Error al leer línea de reserva: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar reservas: " + e.getMessage());
        }
    }
}

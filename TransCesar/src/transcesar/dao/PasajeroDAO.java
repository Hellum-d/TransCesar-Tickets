package transcesar.dao;

import transcesar.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDAO {

    private static final String ARCHIVO = "pasajeros.txt";
    private List<Pasajero> pasajeros = new ArrayList<>();

    public PasajeroDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Pasajero p) {
        pasajeros.add(p);
        guardarEnArchivo(p);
    }

    public List<Pasajero> listar() {
        return pasajeros;
    }

    private void guardarEnArchivo(Pasajero p) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(
                p.getNombre() + ";" +
                p.getIdentificacion() + ";" +
                p.getFechaNacimiento() + ";" +
                p.getClass().getSimpleName()
            );
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar pasajero: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                LocalDate fecha = LocalDate.parse(datos[2]);
                Pasajero p;
                switch (datos[3]) {
                    case "PasajeroEstudiante":
                        p = new PasajeroEstudiante(datos[0], datos[1], fecha);
                        break;
                    case "PasajeroAdultoMayor":
                        p = new PasajeroAdultoMayor(datos[0], datos[1], fecha);
                        break;
                    default:
                        p = new PasajeroRegular(datos[0], datos[1], fecha);
                }
                pasajeros.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar pasajeros: " + e.getMessage());
        }
    }
}
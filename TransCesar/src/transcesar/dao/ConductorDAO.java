package transcesar.dao;

import transcesar.model.Conductor;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    private static final String ARCHIVO = "conductores.txt";
    private List<Conductor> conductores = new ArrayList<>();

    public ConductorDAO() {
        cargarDesdeArchivo();
    }

    public void guardar(Conductor c) {
        conductores.add(c);
        guardarEnArchivo(c);
    }

    public List<Conductor> listar() {
        return conductores;
    }

    private void guardarEnArchivo(Conductor c) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(
                c.getNombre() + ";" +
                c.getIdentificacion() + ";" +
                c.getFechaNacimiento() + ";" +
                c.getLicencia()
            );
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar conductor: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Conductor c = new Conductor(
                    datos[0], datos[1],
                    LocalDate.parse(datos[2]),
                    datos[3]
                );
                conductores.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar conductores: " + e.getMessage());
        }
    }
}
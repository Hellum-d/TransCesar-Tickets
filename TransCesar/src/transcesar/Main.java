package transcesar;

import transcesar.model.*;
import java.util.ArrayList; 

public class Main { 
    public static void main(String[] args) {
        System.out.println("=== SISTEMA TRANSCESAR: PRUEBA DE RUTAS DETALLADAS ===");

        Ruta rutaNorte = new Ruta("RT01", "Valledupar", "Barranquilla", 230.5, 240);
        Ruta rutaSur = new Ruta("RT02", "Valledupar", "Bogotá", 850.0, 960);

 
        Vehiculo bus1 = new Bus("UXX-123", rutaNorte);
        Vehiculo mBus1 = new MicroBus("VYY-456", rutaSur);
        Vehiculo buseta1 = new Buseta("WZZ-789", rutaNorte);

        bus1.imprimirDetalle();
        System.out.println();
        
        mBus1.imprimirDetalle();
        System.out.println();
        
        buseta1.imprimirDetalle();

        System.out.println("======================================================");
        System.out.println("¡Prueba de Integración Exitosa!");
    }
}
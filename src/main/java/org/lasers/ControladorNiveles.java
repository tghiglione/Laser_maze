package org.lasers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ControladorNiveles {

    private final int CANTIDAD_NIVELES = 6;

    private List<Nivel> Niveles;
    private List<Grilla> Grillas;

    public ControladorNiveles() throws Exception {
        cargarNivelesGrillas();
    }

    public List<Nivel> obtenerNiveles(){
        return Niveles;
    };

    public List<Grilla> obtenerGrillas(){
        return Grillas;
    }

    public Nivel obtenerNivel(int numeroNivel){
        return Niveles.get(numeroNivel);
    };

    public Grilla obtenerGrilla(int numeroGrilla){
        return Grillas.get(numeroGrilla);
    }

    private void cargarNivelesGrillas() throws FileNotFoundException {
        for (int i = 1; i <= CANTIDAD_NIVELES;i++) {

            String rutaNivel = "/level" + i + ".dat";
            InputStream nivelStream = getClass().getResourceAsStream(rutaNivel);
            if (nivelStream == null) {
                throw new FileNotFoundException("El archivo level" + i + ".dat no se encontró.");
            }

            Nivel nivel = new Nivel(nivelStream);
            Niveles.add(nivel);
            Grillas.add(nivel.getGrilla());
        }
    }
}

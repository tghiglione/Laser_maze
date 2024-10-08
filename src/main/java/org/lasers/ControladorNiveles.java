package org.lasers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ControladorNiveles {

    public static final int CANTIDAD_NIVELES = 6;

    private List<Nivel> Niveles;
    private List<Grilla> Grillas;

    public ControladorNiveles() throws Exception {
        Niveles = new ArrayList<Nivel>();
        Grillas = new ArrayList<Grilla>();
        cargarNivelesGrillas();
    }

    public List<Nivel> obtenerNiveles(){
        return Niveles;
    };

    public List<Grilla> obtenerGrillas(){
        return Grillas;
    }

    public Nivel obtenerNivel(int numeroNivel){
        return Niveles.get(numeroNivel - 1);
    };

    public Grilla obtenerGrilla(int numeroNivel){
        return Grillas.get(numeroNivel - 1);
    }

    private void cargarNivelesGrillas() throws Exception {
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

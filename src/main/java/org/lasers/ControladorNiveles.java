package org.lasers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorNiveles {

    private final int CANTIDAD_NIVELES = 6;

    private List<Nivel> Niveles;
    private List<Grilla> Grillas;

    public ControladorNiveles() {
        cargarNiveles();
        cargarGrillas();
    }

    public List<Nivel> obtenerNiveles(){
        return Niveles;
    };

    public List<Grilla> obtenerGrillas(){
        return Grillas;
    }

    public Nivel obtenerNivel(int nivel){
        return Niveles.get(nivel);
    };

    public Grilla obtenerGrilla(int grilla){
        return Grillas.get(grilla);
    }

    private void cargarNiveles() {
        for (int i = 1; i <= CANTIDAD_NIVELES;i++) {
            String rutaNivel = "/level" + i + ".dat";
            Niveles.add(new Nivel(getClass().getResourceAsStream(rutaNivel)));
        }
    }

    private void cargarGrillas() {
        for (Nivel nivel : Niveles) {
            Grillas.add(nivel.getGrilla());
        }
    }
}

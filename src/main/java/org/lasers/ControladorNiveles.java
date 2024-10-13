package org.lasers;

import java.io.InputStream;

public class ControladorNiveles {

    private static ControladorNiveles instancia;

    private ControladorNiveles() {
    }

    public static ControladorNiveles obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorNiveles();
        }
        return instancia;
    }

    public Nivel cargarNivel(int numeroNivel) {
        String nombreArchivo = "/level" + numeroNivel + ".dat";
        InputStream nivelStream = getClass().getResourceAsStream(nombreArchivo);

        if (nivelStream == null) {
            throw new RuntimeException("El archivo " + nombreArchivo + " no se encontró.");
        }

        return new Nivel(nivelStream);
    }
}


package org.lasers;

import java.io.InputStream;

public class ControladorNiveles {

    private static ControladorNiveles instancia;

    private ControladorNiveles() {
    }

    //Pre: -
    //Post: devuelve una instancia de la clase ControladorNiveles. Si es null la crea, sino devuelve la existente
    public static ControladorNiveles obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorNiveles();
        }
        return instancia;
    }

    //Pre: debe ser un numero valido que se encuentre en la carpeta de archivos de niveles .Se presupone que los archivos se encuentran bien escritos y estan en la carpeta resources
    //Post: lee y devuelve el nivel segun el numero que le llega
    public Nivel cargarNivel(int numeroNivel) {
        String nombreArchivo = "/level" + numeroNivel + ".dat";
        InputStream nivelStream = getClass().getResourceAsStream(nombreArchivo);

        if (nivelStream == null) {
            throw new RuntimeException("El archivo " + nombreArchivo + " no se encontró.");
        }

        return new Nivel(nivelStream);
    }
}


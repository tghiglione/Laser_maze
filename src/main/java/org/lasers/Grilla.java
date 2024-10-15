package org.lasers;

import java.util.*;

public class Grilla {
    private final Map<Posicion, Celda> celdas;
    private final List<Emisor> emisores;
    private final List<Objetivo> objetivos;

    public Grilla() {
        celdas = new HashMap<>();
        emisores = new ArrayList<>();
        objetivos = new ArrayList<>();
    }

    //Pre: Deve ser una celda valida en la grilla
    //Post: agrega una celda a la grilla en determinada posicion
    public void agregarCelda(Celda celda) {
        Posicion posicion = new Posicion(celda.getCoordenadaX(), celda.getCoordenadaY());
        celdas.put(posicion, celda);
    }

    //Pre: -
    //Post: devuelve la celda en determinada posicion
    public Celda obtenerCeldaEnPosicion(Posicion posicion) {
        return celdas.get(posicion);
    }

    //Pre: -
    //Post: Agrego un emisor en la grilla
    public void agregarEmisor(Emisor emisor) {
        emisores.add(emisor);
    }

    //Pre: -
    //Post: devuelve los emisores de la grilla
    public List<Emisor> getEmisores() {
        return emisores;
    }

    //Pre: -
    //Post: agrega un objetivo en la grilla
    public void agregarObjetivo(Objetivo objetivo) {
        objetivos.add(objetivo);
    }

    //Pre: -
    //Post: devuelve los objetivos de la grilla
    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    //Pre: -
    //Post: devuelve true si todos los objetivos son alcanzados o false si no estan todos alcanzados
    public boolean todosObjetivosAlcanzados() {
        for (Objetivo objetivo : objetivos) {
            if (!objetivo.estaAlcanzado()) {
                return false;
            }
        }
        return true;
    }

    //Pre: -
    //Post: cambia la posicion de las coordenadas de los bloques moviles
    public void moverBloque(Bloque bloque, int nuevaColumna, int nuevaFila) {
        List<Posicion> nuevasPosicionesLogicas = new ArrayList<>();
        Posicion centro = new Posicion(nuevaColumna * 2 + 1, nuevaFila * 2 + 1);
        Posicion arriba = new Posicion(nuevaColumna * 2 + 1, nuevaFila * 2);
        Posicion abajo = new Posicion(nuevaColumna * 2 + 1, nuevaFila * 2 + 2);
        Posicion izquierda = new Posicion(nuevaColumna * 2, nuevaFila * 2 + 1);
        Posicion derecha = new Posicion(nuevaColumna * 2 + 2, nuevaFila * 2 + 1);

        nuevasPosicionesLogicas.add(centro);
        nuevasPosicionesLogicas.add(arriba);
        nuevasPosicionesLogicas.add(abajo);
        nuevasPosicionesLogicas.add(izquierda);
        nuevasPosicionesLogicas.add(derecha);

        bloque.setPosicionesLogicas(nuevasPosicionesLogicas);

    }

    //Pre: -
    //Post: devuelve las posiciones de las celdas de la grilla
    public Map<Posicion, Celda> getCeldas() {
        return celdas;
    }

    //Pre: -
    //Post: devuelve las celdas de la grilla
    public Collection<Celda> obtenerTodasLasCeldas() {
        return celdas.values();
    }

    //Pre: -
    //Post: devuelve las filas de la grilla logica
    private int numeroDeFilas() {
        int maxFila = 0;
        for (Posicion posicion : celdas.keySet()) {
            if (posicion.getY() > maxFila) {
                maxFila = posicion.getY();
            }
        }
        return (maxFila + 1)*2;
    }

    //Pre: -
    //Post: devuelve las columnas de la grilla logica
    private int numeroDeColumnas() {
        int maxColumna = 0;
        for (Posicion posicion : celdas.keySet()) {
            if (posicion.getX() > maxColumna) {
                maxColumna = posicion.getX();
            }
        }
        return (maxColumna + 1)*2;
    }

    //Pre: -
    //Post: devuelve true si se encuentra dentro de la grilla y false si esta fuera
    public boolean estaDentroDeLimites(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();
        return x >= 0 && x < numeroDeColumnas() && y >= 0 && y < numeroDeFilas();
    }
}


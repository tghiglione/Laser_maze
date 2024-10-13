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

    public void agregarCelda(Celda celda) {
        Posicion posicion = new Posicion(celda.getCoordenadaX(), celda.getCoordenadaY());
        celdas.put(posicion, celda);
    }

    public Celda obtenerCeldaEnPosicion(Posicion posicion) {
        return celdas.get(posicion);
    }

    public void agregarEmisor(Emisor emisor) {
        emisores.add(emisor);
    }

    public List<Emisor> getEmisores() {
        return emisores;
    }

    public void agregarObjetivo(Objetivo objetivo) {
        objetivos.add(objetivo);
    }
    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public boolean todosObjetivosAlcanzados() {
        for (Objetivo objetivo : objetivos) {
            if (!objetivo.estaAlcanzado()) {
                return false;
            }
        }
        return true;
    }

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
    public Map<Posicion, Celda> getCeldas() {
        return celdas;
    }
    public Collection<Celda> obtenerTodasLasCeldas() {
        return celdas.values();
    }
    private int numeroDeFilas() {
        int maxFila = 0;
        for (Posicion posicion : celdas.keySet()) {
            if (posicion.getY() > maxFila) {
                maxFila = posicion.getY();
            }
        }
        return (maxFila + 1)*2;
    }

    private int numeroDeColumnas() {
        int maxColumna = 0;
        for (Posicion posicion : celdas.keySet()) {
            if (posicion.getX() > maxColumna) {
                maxColumna = posicion.getX();
            }
        }
        return (maxColumna + 1)*2;
    }

    public boolean estaDentroDeLimites(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();
        return x >= 0 && x < numeroDeColumnas() && y >= 0 && y < numeroDeFilas();
    }
}


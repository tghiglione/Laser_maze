package org.lasers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean moverBloque(Posicion origen, Posicion destino) throws Exception {
        Celda celdaOrigen = obtenerCeldaEnPosicion(origen);
        Celda celdaDestino = obtenerCeldaEnPosicion(destino);

        if (celdaOrigen == null || celdaDestino == null) {
            return false;
        }

        Bloque bloque = celdaOrigen.obtenerBloque();

        if (bloque == null || !bloque.puedeMoverse()) {
            return false;
        }

        if (celdaDestino.estaVacia()) {
            celdaDestino.colocarBloqueEnCelda(bloque);
            celdaOrigen.removerBloque();
            return true;

        }
        return false;
    }
    public Map<Posicion, Celda> getCeldas() {
        return celdas;
    }
}


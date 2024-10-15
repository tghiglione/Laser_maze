package org.lasers;

import java.util.ArrayList;
import java.util.List;

public abstract class Bloque {
    private List<Posicion> posicionesLogicas;  // Coordenadas que ocupa el bloque

    public Bloque() {
        this.posicionesLogicas = new ArrayList<>();
    }

    //Pre: -
    //Post: setea las coordenadas del bloque
    public void setPosicionesLogicas(List<Posicion> nuevasPosiciones) {
        this.posicionesLogicas = nuevasPosiciones;
    }

    //Pre: -
    //Post: devuelve las coordenadas del bloque
    public List<Posicion> getPosicionesLogicas() {
        return posicionesLogicas;
    }

    //Pre: -
    //Post: maneja como interactua el laser con el bloque, cambia la posicion/direccion
    public abstract void interactuarConLaser(Laser laser) throws Exception;

    //Pre: -
    //Post: devuelve true si el bloque es movil o false si es fijo
    public abstract boolean puedeMoverse();
}
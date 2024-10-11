package org.lasers;

import java.util.ArrayList;
import java.util.List;

public abstract class Bloque {
    private List<Posicion> posicionesLogicas;  // Posiciones lógicas que ocupa el bloque

    public Bloque() {
        this.posicionesLogicas = new ArrayList<>();
    }

    public void setPosicionesLogicas(List<Posicion> nuevasPosiciones) {
        this.posicionesLogicas = nuevasPosiciones;
    }

    public List<Posicion> getPosicionesLogicas() {
        return posicionesLogicas;
    }

    public abstract void interactuarConLaser(Laser laser) throws Exception;

    public abstract boolean puedeMoverse();
}
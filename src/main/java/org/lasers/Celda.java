package org.lasers;

public class Celda {
    private boolean tienePiso;
    private Bloque bloque;


    public Celda() {
        this.tienePiso = false;
        this.bloque = null;
    }

    public void asignarPiso() {
        this.tienePiso = true;
    }

    public void colocarBloqueEnCelda(Bloque bloque) {
        if (tienePiso && bloque == null) {
            this.bloque = bloque;
        }
    }

    public void removerBloque() {
        if (this.bloque != null) {
            this.bloque = null;
        }
    }

    public void interactuarConLaser(Laser laser) {
        if (bloque != null) {
            bloque.interactuarConLaser(laser);
        }
    }

    public boolean estaVacia() {
        return this.bloque == null;
    }

    public boolean puedeColocarBloque() {
        return tienePiso && bloque == null;
    }
}
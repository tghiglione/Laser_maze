package org.lasers;

public class Laser {
    private Posicion posicionActual;
    private Direccion direccion;
    private boolean activo;

    public Laser(Posicion posicionInicial, Direccion direccionInicial) {
        this.posicionActual = posicionInicial;
        this.direccion = direccionInicial;
        this.activo = true;
    }

    public void avanzar() throws Exception {
        if (!activo) return;
        // Actualiza la posición actual según la dirección
        posicionActual = posicionActual.posicionAdyacente(direccion);
    }

    public void detener() {
        this.activo = false;
    }

    public void cambiarDireccion(Direccion nuevaDireccion) {
        this.direccion = nuevaDireccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Posicion getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(Posicion nuevaPosicion) {
        this.posicionActual = nuevaPosicion;
    }

    public boolean estaActivo() {
        return activo;
    }
}


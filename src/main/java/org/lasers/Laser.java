package org.lasers;

import java.util.ArrayList;
import java.util.List;

public class Laser {
    private final Posicion posicionInicial;
    private final Direccion direccionInicial;
    private Posicion posicionActual;
    private Direccion direccion;
    private boolean activo;
    private List<Posicion> trayectoria;


    public Laser(Posicion posicionInicial, Direccion direccionInicial) {
        this.direccionInicial = direccionInicial;
        this.posicionInicial = posicionInicial;
        this.posicionActual = posicionInicial;
        this.direccion = direccionInicial;
        this.activo = true;
        this.trayectoria = new ArrayList<>();
        trayectoria.add(posicionInicial);
    }

    public void avanzar() throws Exception {
        if (!activo) return;
        Posicion nuevaPos = posicionActual.posicionAdyacente(direccion);
        trayectoria.add(nuevaPos);
        setPosicionActual(nuevaPos);

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

    public List<Posicion> getTrayectoria() {
        return new ArrayList<>(trayectoria);
    }

    public void reiniciarTrayectoria() {
        trayectoria.clear();
        posicionActual = posicionInicial;
        direccion=direccionInicial;
        this.activo = true;
        trayectoria.add(posicionActual);
    }
}


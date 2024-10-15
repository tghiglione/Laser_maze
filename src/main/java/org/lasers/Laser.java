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

    //Pre: -
    //Post: Avanza la posicion del laser en determinada direccion
    public void avanzar() throws Exception {
        if (!activo) return;
        Posicion nuevaPos = posicionActual.posicionAdyacente(direccion);
        trayectoria.add(nuevaPos);
        setPosicionActual(nuevaPos);

    }
    //Pre: -
    //Post: detiene el laser
    public void detener() {
        this.activo = false;
    }

    //Pre: -
    //Post: Cambia la direccion del laser
    public void cambiarDireccion(Direccion nuevaDireccion) {
        this.direccion = nuevaDireccion;
    }

    //Pre: -
    //Post: devuelve la direccion actual del laser
    public Direccion getDireccion() {
        return direccion;
    }

    //Pre: -
    //Post: devuelve la posicion actual del laser
    public Posicion getPosicionActual() {
        return posicionActual;
    }

    //Pre: -
    //Post: ccambia la posicion actual del laser
    public void setPosicionActual(Posicion nuevaPosicion) {
        this.posicionActual = nuevaPosicion;
    }

    //Pre: -
    //Post: devuelve true si el laser esta activo o false si esta detenido
    public boolean estaActivo() {
        return activo;
    }

    //Pre: -
    //Post: devuelve la trayectoria del laser, osea las posiciones que ese laser paso
    public List<Posicion> getTrayectoria() {
        return new ArrayList<>(trayectoria);
    }

}


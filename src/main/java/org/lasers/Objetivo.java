package org.lasers;

public class Objetivo {
    private final Posicion posicion;
    private boolean esAlcanzado;

    public Objetivo(Posicion posicion){
        this.posicion = posicion;
        this.esAlcanzado = false;
    }

    public boolean estaAlcanzado(){
        return esAlcanzado;
    }

    public Posicion getPosicionObjetivo() {
        return posicion;
    }
    public void setAlcanzado(boolean alcanzado){
        esAlcanzado = alcanzado;
    }
}

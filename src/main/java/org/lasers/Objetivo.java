package org.lasers;

public class Objetivo {
    private final Posicion posicion;
    private boolean esAlcanzado;

    public Objetivo(Posicion posicion){
        this.posicion = posicion;
        this.esAlcanzado = false;
    }

    //Pre: -
    //Post: devuelve si esta alcanzado o no ese objetivo
    public boolean estaAlcanzado(){
        return esAlcanzado;
    }

    //Pre: -
    //Post: devuelve la posicion del objetivo
    public Posicion getPosicionObjetivo() {
        return posicion;
    }

    //Pre: -
    //Post: actualiza el objetivo como alcanzado en true y si no esta alcanzado en false
    public void setAlcanzado(boolean alcanzado){
        esAlcanzado = alcanzado;
    }
}

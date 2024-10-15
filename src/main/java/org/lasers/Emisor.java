package org.lasers;

public class Emisor {
    private final Posicion posicion;
    private final Direccion direccion;

    public Emisor(Posicion posicion, Direccion direccion) {
        this.posicion = posicion;
        this.direccion = direccion;
    }

    //Pre: -
    //Post: devuelve un laser que se posiciona en el emisor
    public Laser emitirLaser() {
        return new Laser(posicion, direccion);
    }

    //Pre: -
    //Post: devuelve la posicion del emisor
    public Posicion getPosicionEmisor() {
        return posicion;
    }

}

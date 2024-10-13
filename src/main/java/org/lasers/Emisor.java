package org.lasers;

public class Emisor {
    private final Posicion posicion;
    private final Direccion direccion;

    public Emisor(Posicion posicion, Direccion direccion) {
        this.posicion = posicion;
        this.direccion = direccion;
    }

    public Laser emitirLaser() {
        return new Laser(posicion, direccion);
    }

    public Posicion getPosicionEmisor() {
        return posicion;
    }

}

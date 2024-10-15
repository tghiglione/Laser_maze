package org.lasers;

import java.util.Objects;

public class Posicion {
    private final int x;
    private final int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //Pre: -
    //Post: devuelve la nueva posicion adyacente del laser segun la direccion
    public Posicion posicionAdyacente(Direccion direccion) throws Exception{
        switch (direccion) {
            case NE:
                return new Posicion(x + 1, y - 1);
            case NW:
                return new Posicion(x - 1, y - 1);
            case SE:
                return new Posicion(x + 1, y + 1);
            case SW:
                return new Posicion(x - 1, y + 1);
            default:
                throw new Exception("Dirección desconocida");
        }
    }
    //Pre: -
    //Post: devuelve la coordenada x
    public int getX() {
        return x;
    }
    //Pre: debe haber una celda valida
    //Post: devuelve la coordenada y
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicion)) return false;
        Posicion other = (Posicion) obj;
        return this.x == other.x && this.y == other.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}


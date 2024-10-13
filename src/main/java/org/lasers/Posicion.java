package org.lasers;

import java.util.Objects;

public class Posicion {
    private final int x;
    private final int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    public int getX() {
        return x;
    }

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


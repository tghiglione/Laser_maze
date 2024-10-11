package org.lasers;

public class BloqueEspejo extends Bloque {
    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public void interactuarConLaser(Laser laser) throws Exception {
        Direccion direccionActual = laser.getDireccion();
        Posicion posicionLaser = laser.getPosicionActual();
        Direccion nuevaDireccion = direccionActual.direccionReflejada(posicionLaser, direccionActual);
        laser.cambiarDireccion(nuevaDireccion);
    }
}

package org.lasers;

public class BloqueEspejo implements Bloque {
    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public void interactuarConLaser(Laser laser) throws Exception {
        Direccion direccionActual = laser.getDireccion();
        Direccion nuevaDireccion = direccionActual.direccionReflejada();
        laser.cambiarDireccion(nuevaDireccion);
    }
}

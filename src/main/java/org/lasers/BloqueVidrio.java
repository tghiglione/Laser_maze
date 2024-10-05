package org.lasers;

public class BloqueVidrio implements Bloque {
    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public void interactuarConLaser(Laser laser) throws Exception {
        Direccion direccionActual = laser.getDireccion();
        Direccion direccionReflejada = direccionActual.direccionReflejada();
        Laser laserReflejado = new Laser(laser.getPosicionActual(), direccionReflejada);
        GestorLasers.obtenerInstancia().agregarLaser(laserReflejado);
    }
}

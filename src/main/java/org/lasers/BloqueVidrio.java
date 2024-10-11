package org.lasers;

public class BloqueVidrio extends Bloque {
    @Override
    public boolean puedeMoverse() {
        return true;
    }

    @Override
    public void interactuarConLaser(Laser laser) throws Exception {
        Direccion direccionActual = laser.getDireccion();
        Posicion posicionLaser = laser.getPosicionActual();
        Direccion direccionReflejada = direccionActual.direccionReflejada(posicionLaser, direccionActual);
        Laser laserReflejado = new Laser(laser.getPosicionActual(), direccionReflejada);
        GestorLasers.obtenerInstancia().agregarLaser(laserReflejado);
    }
}